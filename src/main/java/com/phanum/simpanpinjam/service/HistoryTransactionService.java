/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.service;

import com.phanum.simpanpinjam.model.HistoryTransaction;
import static com.phanum.simpanpinjam.model.HistoryTransaction.SEQUENCE_NAME;
import com.phanum.simpanpinjam.model.Member;
import com.phanum.simpanpinjam.model.Transaction;
import com.phanum.simpanpinjam.repository.HistoryTransactionRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author panha
 */
@Service
public class HistoryTransactionService {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private HistoryTransactionRepository historyTransactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MemberService memberService;

    public List<HistoryTransaction> getAllHistory() {
        List<HistoryTransaction> historyTransactions = new ArrayList<HistoryTransaction>();
        historyTransactionRepository.findAll().forEach(historyTransactions::add);

        for (HistoryTransaction historyTransaction : historyTransactions) {
            Optional<Member> member = memberService.getById(historyTransaction.getMember_id());
            if (member.isPresent()) {
                historyTransaction.setMember(member.get());

            }
            Optional<Transaction> transaction = transactionService.getById(historyTransaction.getTransaction_id());

            if (transaction.isPresent()) {
                historyTransaction.setTransaction(transaction.get());

            }
        }
        return historyTransactions;
    }

    public List<HistoryTransaction> getHistoryByMemberId(UUID memberId) {
        List<HistoryTransaction> historyTransactions = historyTransactionRepository.findByMemberId(memberId);
        for (HistoryTransaction historyTransaction : historyTransactions) {
            Optional<Member> member = memberService.getById(historyTransaction.getMember_id());
            if (member.isPresent()) {
                historyTransaction.setMember(member.get());

            }
            Optional<Transaction> transaction = transactionService.getById(historyTransaction.getTransaction_id());

            if (transaction.isPresent()) {
                historyTransaction.setTransaction(transaction.get());

            }
        }
        return historyTransactions;
    }

    public HistoryTransaction getHistoryById(UUID id) {
        Optional<HistoryTransaction> historyTransaction = historyTransactionRepository.findById(id);
        if (historyTransaction.isPresent()) {
            return historyTransaction.get();
        }
        return new HistoryTransaction();
    }

    public HistoryTransaction saveHistoryTransaction(HistoryTransaction historyTransaction) {
        historyTransaction.setId(sequenceGeneratorService.getSequenceNumber(SEQUENCE_NAME));
        return historyTransactionRepository.save(historyTransaction);
    }
}
