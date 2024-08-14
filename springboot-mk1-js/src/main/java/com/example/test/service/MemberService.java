package com.example.test.service;

import org.springframework.stereotype.Service;

import com.example.test.model.Member;
import com.example.test.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
  private final MemberRepository memberRepository;

  @Transactional
  public void saveMember(Member member){
    memberRepository.save(member);
  }
}
