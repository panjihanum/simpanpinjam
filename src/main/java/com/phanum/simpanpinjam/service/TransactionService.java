package com.phanum.simpanpinjam.service;

import com.phanum.simpanpinjam.constant.TransactionConstant;
import com.phanum.simpanpinjam.engine.Producer;
import com.phanum.simpanpinjam.model.Member;
import com.phanum.simpanpinjam.model.Transaction;
import com.phanum.simpanpinjam.repository.TransactionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author panha
 */
@Service
public class TransactionService {

    @Autowired
    Producer producer;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TransactionRepository transactionRepository;

    public Optional<Transaction> getById(UUID id) {
        return transactionRepository.findById(id);
    }

    public List<Transaction> getAllTransaction() {
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactionRepository.findAll().forEach(transactions::add);

        return transactions;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction sendDeposit(UUID memberId, int amount, String comment) {
        Optional<Member> member = memberService.getById(memberId);
        Transaction transaction = new Transaction();
        if (member.isPresent()) {
            transaction = new Transaction(member.get(), amount, comment);
            transaction.setTransactionType(TransactionConstant.DEPOSIT);
            transaction.setStatus(TransactionConstant.PENDING);
            transaction = saveTransaction(transaction);
            producer.sendTransactionLog(transaction);

            Member _member = member.get();
            _member.setLoanLimit(_member.getLoanLimit() + (transaction.getAmount() * 12 / 100));
            memberService.saveMember(_member);
        }

        return transaction;
    }

    public Transaction sendKredit(UUID memberId, int amount, String comment) {
        Optional<Member> member = memberService.getById(memberId);
        Transaction transaction = new Transaction();
        if (member.isPresent()) {
            int balance = transactionRepository.getBalance(member.get().getId()).get(0);
            if (balance >= amount) {
                transaction = new Transaction(member.get(), amount, comment);
                transaction.setTransactionType(TransactionConstant.BORROW);
                transaction.setStatus(TransactionConstant.PENDING);
                transaction = saveTransaction(transaction);
                producer.sendTransactionLog(transaction);
            }
        }

        return transaction;
    }

    public Transaction sendBorrow(UUID memberId, int amount, String comment) {
        Optional<Member> member = memberService.getById(memberId);
        Transaction transaction = new Transaction();
        if (member.isPresent()) {
            int usageLoanService = transactionRepository.getLoanUsage(member.get().getId()).get(0);
            if ((member.get().getLoanLimit() - usageLoanService) >= amount) {
                transaction = new Transaction(member.get(), amount, comment);
                transaction.setTransactionType(TransactionConstant.BORROW);
                transaction.setStatus(TransactionConstant.PENDING);
                transaction = saveTransaction(transaction);
                producer.sendTransactionLog(transaction);
            }
        }

        return transaction;
    }

    public Transaction sendPay(UUID memberId, int amount, String comment) {
        Optional<Member> member = memberService.getById(memberId);
        Transaction transaction = new Transaction();
        if (member.isPresent()) {
            int balance = transactionRepository.getBalance(member.get().getId()).get(0);
            if (balance >= amount) {
                int usageLoanService = transactionRepository.getLoanUsage(member.get().getId()).get(0);
                // Jumlah yang dibayar lebih dari jumlah pinjaman
                if ((member.get().getLoanLimit() - usageLoanService) <= amount) {
                    transaction = new Transaction(member.get(), amount, comment);
                    transaction.setTransactionType(TransactionConstant.BORROW);
                    transaction.setStatus(TransactionConstant.PENDING);
                    transaction = saveTransaction(transaction);
                    producer.sendTransactionLog(transaction);
                }
            }

        }

        return transaction;
    }
}
