/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.engine;

import com.phanum.simpanpinjam.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author panha
 */
@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "transaction";

    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    public void sendTransactionLog(Transaction transaction) {
        logger.info(String.format("Send Transaction -> %s", transaction));
        this.kafkaTemplate.send(TOPIC, transaction);
    }
}
