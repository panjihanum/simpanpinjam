/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.service;

import com.phanum.simpanpinjam.model.Member;
import com.phanum.simpanpinjam.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author panha
 */
@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;
    
    public List<Member> getAllMember() {
        List<Member> members = new ArrayList<Member>();
        memberRepository.findAll().forEach(members::add);
        
        return members;
    }
    
    public Optional<Member> getById(UUID id) {
        return memberRepository.findById(id);
    }
    
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
}
