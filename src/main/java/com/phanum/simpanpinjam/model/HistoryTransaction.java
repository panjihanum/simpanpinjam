/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author panha
 */
@Document(collection = "historyTransactions")
public class HistoryTransaction {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private int id;

    private UUID member_id;
    private UUID source_id;
    private UUID transaction_id;
    private Date createdAt;
    private Date updatedAt;

    private Member member;
    private Transaction transaction;

    public HistoryTransaction() {
    }

    public HistoryTransaction(UUID member_id, UUID transaction_id) {
        this.member_id = member_id;
        this.transaction_id = transaction_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getMember_id() {
        return member_id;
    }

    public void setMember_id(UUID member_id) {
        this.member_id = member_id;
    }

    public UUID getSource_id() {
        return source_id;
    }

    public void setSource_id(UUID source_id) {
        this.source_id = source_id;
    }

    public UUID getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(UUID transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
