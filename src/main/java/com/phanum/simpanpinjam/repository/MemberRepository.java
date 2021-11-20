/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.repository;

import com.phanum.simpanpinjam.model.Member;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author panha
 */
public interface MemberRepository extends JpaRepository<Member, UUID> {


}
