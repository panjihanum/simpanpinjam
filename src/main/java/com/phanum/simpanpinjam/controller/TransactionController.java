/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.controller;

import com.phanum.simpanpinjam.model.Transaction;
import com.phanum.simpanpinjam.service.TransactionService;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author panha
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping(
            value = "/deposit",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> doDeposit(
            @RequestParam(required = true) UUID memberId,
            @RequestParam(required = true) int amount,
            @RequestParam(required = true) String comment) {
        try {

            Transaction transaction = transactionService.sendDeposit(memberId, amount, comment);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(
            value = "/kredit",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> doKredit(
            @RequestParam(required = true) UUID memberId,
            @RequestParam(required = true) int amount,
            @RequestParam(required = true) String comment) {
        try {

            Transaction transaction = transactionService.sendKredit(memberId, amount, comment);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(
            value = "/borrow",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> doBorrow(
            @RequestParam(required = true) UUID memberId,
            @RequestParam(required = true) int amount,
            @RequestParam(required = true) String comment) {
        try {

            Transaction transaction = transactionService.sendBorrow(memberId, amount, comment);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(
            value = "/pay",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Transaction> doPay(
            @RequestParam(required = true) UUID memberId,
            @RequestParam(required = true) int amount,
            @RequestParam(required = true) String comment) {
        try {

            Transaction transaction = transactionService.sendPay(memberId, amount, comment);
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
