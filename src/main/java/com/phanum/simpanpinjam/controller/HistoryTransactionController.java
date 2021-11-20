/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.controller;

import com.phanum.simpanpinjam.model.HistoryTransaction;
import com.phanum.simpanpinjam.model.Member;
import com.phanum.simpanpinjam.service.HistoryTransactionService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author panha
 */
@RestController
@RequestMapping("/history-transaction")
public class HistoryTransactionController {

    @Autowired
    HistoryTransactionService historyTransactionService;

    @GetMapping("")
    public ResponseEntity<List<HistoryTransaction>> getAllTransaction() {
        try {
            List<HistoryTransaction> historyTransactions = historyTransactionService.getAllHistory();

            if (historyTransactions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(historyTransactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<HistoryTransaction>> getTransaction(@PathVariable(required = true) UUID memberId) {
        try {
            List<HistoryTransaction> historyTransactions = historyTransactionService.getHistoryByMemberId(memberId);

            return new ResponseEntity<>(historyTransactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
