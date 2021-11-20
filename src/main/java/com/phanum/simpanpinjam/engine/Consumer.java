/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.engine;

import com.phanum.simpanpinjam.constant.TransactionConstant;
import com.phanum.simpanpinjam.model.HistoryTransaction;
import com.phanum.simpanpinjam.model.Transaction;
import com.phanum.simpanpinjam.service.HistoryTransactionService;
import com.phanum.simpanpinjam.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 *
 * @author panha
 */
@Service
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private HistoryTransactionService historyTransactionService;

    @KafkaListener(topics = "transaction", groupId = "group_id")
    public void consume(Transaction transaction) throws IOException {
        logger.info(String.format("Receive Transaction -> %s", transaction));
        transaction.setStatus(TransactionConstant.SUCCESS);
        transactionService.saveTransaction(transaction);
        HistoryTransaction historyTransaction = new HistoryTransaction(transaction.getMemberId().getId(), transaction.getId());
        historyTransactionService.saveHistoryTransaction(historyTransaction);
    }
}
