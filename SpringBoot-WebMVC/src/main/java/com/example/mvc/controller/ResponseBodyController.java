package com.example.mvc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvc.model.MemberData;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ResponseBodyController {
	
	// 메시지 바디와 함께 상태 코드도 반환하기
	@GetMapping("responseBody-v1")
	public ResponseEntity<String> responseBodyV1(){
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}

	// 메시지 바디만 반환하기
	@GetMapping("responseBody-v2")
	public String responseBodyV2(){
		return "ok";
	}
	
	// 객체 타입도 반환 가능
	// 반환 결과는 json 형태임
	@GetMapping("responseBody-v3")
	public ResponseEntity<MemberData> responseBodyV3(){
		MemberData data = new MemberData();
		data.setId("홍길동");
		data.setName("홍홍길길동동");
		data.setAge(222555);
		
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	// 얘도 상태값 없이 메시지만 보낼 수 있음
	@GetMapping("responseBody-v4")
//	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public MemberData responseBodyV4(){
		MemberData data = new MemberData();
		data.setId("홍길동");
		data.setName("홍홍길길동동");
		data.setAge(222555);
		
		return data;
	}
}
