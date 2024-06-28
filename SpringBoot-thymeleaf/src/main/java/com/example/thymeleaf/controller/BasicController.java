package com.example.thymeleaf.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.thymeleaf.model.MemberData;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("basic")
public class BasicController {
	
	@GetMapping("/text-basic")
	public String textBasic(Model model) { // model.addAttribute
		model.addAttribute("data", "hello spring");
		return "basic/text-basic";
	}
	
	@GetMapping("/text-unescaped")
	public String textUnescaped(Model model) {
		model.addAttribute("data", "Hello <b>Spring</b>");
		return "basic/text-unescaped";
	}
	
	@GetMapping("/variable")
	public String variable(Model model) {
		MemberData member1 = new MemberData("홍", "홍길동", 20);
		MemberData member2 = new MemberData("손", "손오공", 40);
		
		List<MemberData> memberList = new ArrayList<>();
		memberList.add(member1);
		memberList.add(member2);
		
		Map<String, MemberData> memberMap = new HashMap<>();
		memberMap.put("member1", member1);
		memberMap.put("member2", member2);
		
		model.addAttribute("member", member1); // 객체 담아보기
		model.addAttribute("memberList", memberList); // 리스트 담아보기
		model.addAttribute("memberMap", memberMap); // 맵 담아보기
		
		return "basic/variable";
	}
	
	@GetMapping("/basic-objects")
	public String basicObjects(HttpSession session) {
		session.setAttribute("sessionData", "로드인 중...");
		return "basic/basic-objects";
	}
	
	@GetMapping("/date")
	public String date(Model model) {
		model.addAttribute("localDateTime", LocalDateTime.now());
		return "basic/date";
	}
	
	@GetMapping("/link")
	public String link(Model model) {
		model.addAttribute("param1", "100");
		model.addAttribute("param2", "hello");
		return "basic/link";
	}
	
	@GetMapping("/literal")
	public String literal(Model model) {
		model.addAttribute("data", "Spring");
		return "basic/literal";
	}
	
	@GetMapping("/operation")
	public String operation(Model model) {
		model.addAttribute("data", "데이터");
		model.addAttribute("nullData", null);
		return "basic/operation";
	}
	
	@GetMapping("/attribute")
	public String attribute() {
		return "basic/attribute";
	}
	
	@GetMapping("/each")
	public String each(Model model) {
		addMembers(model);
		return "basic/each";
	}
	
	private void addMembers(Model model) {
		List<MemberData> memberList = new ArrayList<>();
		memberList.add(new MemberData("홍", "홍길동", 20));
		memberList.add(new MemberData("김", "김길동", 10));
		memberList.add(new MemberData("이", "이길동", 30));
		memberList.add(new MemberData("박", "박길동", 40));
		memberList.add(new MemberData("최", "최길동", 50));
		model.addAttribute("memberList", memberList);
	}
	
	@GetMapping("/condition")
	public String condition(Model model) {
		addMembers(model);
		return "basic/condition";
	}
	
	@GetMapping("/block")
	public String block(Model model) {
		addMembers(model);
		return "basic/block";
	}
	
	@GetMapping("/js")
	public String js(Model model) {
		model.addAttribute("member", new MemberData("jeong", "정길동", 30));
		addMembers(model);
		return "basic/js";
	}
}
