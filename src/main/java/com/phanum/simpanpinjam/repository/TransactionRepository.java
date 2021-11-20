/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.repository;

import com.phanum.simpanpinjam.model.Transaction;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * transactionRepository.getLoanUsage(member.get().getId()
 * transactionRepository.getLoanUsage(member.get().getId()).get(0)
 *
 * @author panha
 */
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(amount),0)  as count FROM (SELECT SUM(amount) as amount FROM transactions as t1 WHERE t1.status='SUCCESS' and t1.transaction_type='3' and t1.member_id=:member_id UNION SELECT SUM(amount * -1) as amount FROM transactions as t2 WHERE t2.status='SUCCESS' and t2.transaction_type='4'  and t2.member_id=:member_id) as amount")
    List<Integer> getLoanUsage(@Param("member_id") UUID member_id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT COALESCE(SUM(amount),0)  as count FROM (SELECT SUM(amount) as amount FROM transactions as t1 WHERE t1.status='SUCCESS' and t1.transaction_type='1' and t1.member_id=:member_id  UNION SELECT SUM(amount * -1) as amount FROM transactions as t2 WHERE t2.status='SUCCESS' and t2.transaction_type='2'  and t2.member_id=:member_id) as amount")
    List<Integer> getBalance(@Param("member_id") UUID member_id);
}
