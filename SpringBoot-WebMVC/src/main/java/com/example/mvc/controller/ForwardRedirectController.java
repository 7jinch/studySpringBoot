package com.example.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ForwardRedirectController {
	
	@GetMapping("forward")
	public String forwrard(@RequestParam("param1") String param1) {
		log.info("forward 실행");
		log.info("forward parma1: {}", param1);
		return "forward:/nexturl";
		// forward:를 붙이면 파라미터를 담은 채로 nexturl로 매핑을 시도함
		// 컨트롤러를 구분하지 않고 같은 url(nexturl)이 매핑된 메서드(nexturl)를 찾아다님
	}
	
	@GetMapping("nexturl")
	public String nexturl(@RequestParam(value = "param1", required = false) String param1, Model model) {
		log.info("nexturl param1: {}", param1);
		return "response/next";
	}
	
	@GetMapping("redirect")
	public String redirect(@RequestParam("param1") String param1) {
		log.info("redirect param1: {}", param1);
		return "redirect:/nexturl";
		// redirect:
		// 웹 브라우저 -> 요청 -> 컨트롤러 -> 메서드 1 실행 -> 응답 -> 웹 브라우저 -> 요청 -> 컨트롤러 -> 메서드 2 실행 -> 응답 -> 컨트롤러
	}
	// forward, redirect 차이
	// forward: 서버에서 다시 요청 -> request 객체가 살아있음(파라미터 값 등)
	// redirect: 클라이언트에서 다시 요청 -> 기존의 request 객체가 없어짐
}
