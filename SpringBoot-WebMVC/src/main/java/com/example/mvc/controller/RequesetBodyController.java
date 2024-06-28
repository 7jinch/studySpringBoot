package com.example.mvc.controller;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvc.model.MemberData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RequesetBodyController {
	
	// httpEntity.getBody()를 사용해서 메시지 바디를 반환받기
	@PostMapping("requestbody-json-v1")
	public String RequestBodyJsonV1(HttpEntity<String> httpEntity) {
		log.info("httpEntity: {}", httpEntity);
		log.info("message: {}", httpEntity.getBody());
		return "ok";
	}

	// @RequestBody 어노테이션을 통해 메시지 바디 반환받기
	@PostMapping("requestbody-json-v2")
	public String RequestBodyJsonV2(@RequestBody String messageBody) throws JsonMappingException, JsonProcessingException {
		log.info("message: {}", messageBody);
		ObjectMapper om = new ObjectMapper();
		MemberData member = om.readValue(messageBody, MemberData.class);
		log.info("member: {}: ", member);
		return "ok";
	}

	// @RequestBody는 객체 타입의 파라미터도 읽어올 수 있음
	@PostMapping("requestbody-json-v3")
	public String RequestBodyJsonV3(@RequestBody MemberData member) {
		log.info("message: {}", member);
		return "ok";
	}
	
	// api 만들어보기
	@PostMapping("requestbody-json-v4")
	public MemberData RequestBodyJsonV4(@RequestBody MemberData member) {
		log.info("message: {}", member);
		member.setId("aa");
		return member;
	}
}
