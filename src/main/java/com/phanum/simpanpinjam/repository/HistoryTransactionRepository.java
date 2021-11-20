/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.repository;

import com.phanum.simpanpinjam.model.HistoryTransaction;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author panha
 */
public interface HistoryTransactionRepository extends MongoRepository<HistoryTransaction, UUID> {

    @Query("{'member_id' : { '$eq' : ?0 }}")
    List<HistoryTransaction> findByMemberId(@Param("member_id") UUID member_id);
}
