package com.example.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import com.example.board.model.member.Member;
import com.example.board.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Slf4j
@Service
public class MemberService {
	
	private MemberRepository memberRepository;
	
	@Autowired
	private void setMemberRepository(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	// 트랜잭션 관리는 service에서
	@Transactional // 혹시라도 터졌다면 롤백해라
	public void saveMember(Member member) {
		memberRepository.save(member);
	}

	public Member findMemberById(String member_id) {
		Optional<Member> member = memberRepository.findById(member_id);
		return member.orElse(null);
	}
}
