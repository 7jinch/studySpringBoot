package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.model.member.Member;
import com.example.board.model.member.MemberJoinForm;
import com.example.board.model.member.MemberLoginForm;
import com.example.board.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("member")
public class MemberController {
	
//	@Autowired // 필드 주입 방식
	private MemberService memberService;

	@Autowired // setter 주입 방식
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	// 회원가입
	@GetMapping("join")
	public String joinForm(Model model) {
		model.addAttribute("member", new MemberJoinForm());
		return "member/joinForm";
	}
	
	@PostMapping("join")
	public String join(@Validated @ModelAttribute(value="member") MemberJoinForm memberJoinForm, // @Validated를 붙여주면 뒤에 있는 객체를 유효성 검사함
						BindingResult result, // BindingResult 선언만 해줘도 에러가 몇 개가 터지든 이 친구가 잡아줘서 사용자에게는 에러가 보이지 않고 프로그램이 안 멈추고 그대로 흘러감
						Model model) {
		log.info("입력값: {}", memberJoinForm);
		log.info("result: {}", result);

		if(result.hasErrors()) { // 에러가 발생했다면 바로 리턴
//			model.addAttribute("에러메시지", "아이디가 형식에 안 맞음");
			return "member/joinForm";
		}
		
		// 이메일 주소 검증
		if(!memberJoinForm.getEmail().contains("@")) {
//			model.addAttribute("에러메시지", "이메일을 정확히 입력하십시오");
			result.reject("이메일 에러", "이메일 형식이 안 맞음"); // 에러코드와 에러메시지를 입력할 수 있읍
			return "member/joinForm";
		}
		
		// memberJoinForm을 Member로 변환하기
		// MemberJoinForm와 Member는 형태는 같지만
		// MemberJoinForm은 유효성 검사를 위한 자바 클래스이고 Member는 엔티티라서 바로 못 넣기 때문에 변환해줘야 함
		Member member = MemberJoinForm.toMember(memberJoinForm);
		
		// id 체크
		Member findMember = memberService.findMemberById(member.getMember_id());
		if(findMember == null) { // 해당 id의 회원이 없으면
			memberService.saveMember(member); // 회원가입 진행
			log.info("회원가입 성공");
			return "redirect:/list";
		} else if(findMember != null) {
//			model.addAttribute("에러메시지", "아이디 중복");
			result.reject("아이디 에러", "아이디 중복");
			return "member/joinForm";
		}
		log.info("회원가입 실패");
		return "redirect:/list";
	}
	
	// 로그인 페이지 이동
	@GetMapping("login")
	public String loginForm(Model model) {
		model.addAttribute("loginForm", new Member());
		return "member/loginForm";
	}
	
	@PostMapping("login")
	public String login(@Validated @ModelAttribute(value="loginForm") MemberLoginForm memberLoginForm,
						BindingResult result, Model model) {
		log.info("입력값: {}", memberLoginForm);
		
		if(result.hasErrors()) {
			return "member/loginForm";
		}
		
		// 로그인 검증
		// 1. db에 가서 id가 member 테이블에 있는지 확인
		Member findMember = memberService.findMemberById(memberLoginForm.getMember_id());
		
		// 2. 그 id가 가지고 있는 password와 입력받은 password가 일치하는지 확인
		if(findMember == null) {
			result.reject("로그인 에러", "아이디나 비밀번호를 제대로 입력하시오");
			return "member/login";
		} else if(findMember.getPassword().equals(memberLoginForm.getPassword())) {
			result.reject("로그인 에러", "아이디 또는 비밀번호가 틀림");
			return "member/login";
		}

		// 3. 맞으면 로그인, 다르면 reject()
		
		
		model.addAttribute("로그인", "로그인 성공");
		return "redirect:/";
	}
}
