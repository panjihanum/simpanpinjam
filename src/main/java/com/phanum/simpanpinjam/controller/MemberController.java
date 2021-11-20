/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.phanum.simpanpinjam.controller;

import com.phanum.simpanpinjam.model.Member;
import com.phanum.simpanpinjam.service.MemberService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author panha
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    
    @Autowired
    MemberService memberService;
    
    @GetMapping("")
    public ResponseEntity<List<Member>> getAllMembers(@RequestParam(required = false) String title) {
        try {
            List<Member> members = memberService.getAllMember();
            
            if (members.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(members, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("")
    public ResponseEntity<Member> createMember(
            @RequestParam(required = true) String name,
            @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateOfBirth,
            @RequestParam(required = true) String address) {
        try {
            Member member = new Member(name, dateOfBirth, address);
            member.setLoanLimit(100000);
            member = memberService.saveMember(member);
            
            return new ResponseEntity<>(member, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
