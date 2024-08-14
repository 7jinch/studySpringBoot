package com.example.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.test.dto.MemberSignupDTO;
import com.example.test.model.Member;
import com.example.test.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
//@CrossOrigin(origins="http://localhost:5173") // 허용할 도메인
public class MemberRestController {
  private final MemberService memberService;
  
  @PostMapping
  public ResponseEntity<String> signup(@RequestBody MemberSignupDTO signupMember){
    log.info("받은 데이터: {}", signupMember);
    Member member = new Member(signupMember);
    memberService.saveMember(member);

    return ResponseEntity.ok("ok");
  }
}
