package com.example.mvc.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mvc.model.MemberData;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RequestParamController {

//	@ResponseBody
	@RequestMapping("request-parma-v1")
	// Servlet 방식: HttpServletRequest
	public String requestParamV1(HttpServletRequest request) {
		log.info("request-param-v1 실행");
		
		// params 가져오기
		log.info("request: {}", request);
		String id = request.getParameter("id");
		log.info("id: {}", id);
		String ki = request.getParameter("ki");
		log.info("ki: {}", ki);
		String mommuge = request.getParameter("mommuge");
		log.info("mommuge: {}", mommuge);
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("request-param-v2")
	public String requestParamV2(@RequestParam(value="id", defaultValue="신규") String id,
								 @RequestParam("name") String name,
								 @RequestParam(value="age", defaultValue="신규") String age) {
		log.info("request-param-v2 실행");
		
		log.info("id: {}", id);
		log.info("name: {}", name);
		log.info("age: {}", age);
		return "ok";
	}

	@ResponseBody
	@RequestMapping("request-param-v3")
	public String requestParamV3(@RequestParam Map<String, String> paramMap) {
		log.info("request-param-v3 실행");
		
		String name = paramMap.get("name");
		log.info("상품명: {}", name);
		String description = paramMap.get("desc");
		log.info("상품 소개: {}", description);
		return "ok";
	}
	
	@ResponseBody
	@PostMapping("modelattribute-v1")
	public String modelAttributeV1(@ModelAttribute MemberData member) {
	    log.info("modelAttribute v1 실행");
	    log.info("member: {}", member);
	    return "됨?";
	}

	
	public String name() {
		return "signup";
	}
}
