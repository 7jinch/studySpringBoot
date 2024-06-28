package com.example.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestMappingController {
	
	// 로거
	private final Logger log = LoggerFactory.getLogger(RequestMappingController.class);
	
	// 예전 방식(여전히 동작하고 사용도 함)
//	@RequestMapping("hello")
//	public String hello() {
//		return "Hello World!";
//	}
	// get 방식
//	@RequestMapping(path="hello-get", method=RequestMethod.GET)
//	public String helloGet() {
//		log.info("helloGet() 실행!");
//		return "Hello Get!";
//	}
	// post 방식
//	@RequestMapping(path="hello-post", method=RequestMethod.POST)
//	public String helloPost() {
//		log.info("helloPost() 실행!");
//		return "Hello Post!";
//	}

	// 요즘 방식
	// get 방식
	@GetMapping(path="hello-get")
	public String helloGet() {
		log.info("helloGet() 실행!");
		return "Hello Get!";
	}

	// post 방식
	@PostMapping(path="hello-post")
	public String helloPost() {
		log.info("helloPost() 실행!");
		return "Hello Post!";
	}

	// 배열로 받기
	// path도 여러개 받고 요청 메서드도 여러개 받기
	@RequestMapping(path={"hello-get-post", "hello-post-get"}, method={RequestMethod.GET, RequestMethod.POST})
	public String helloArray() {
		log.info("get, post 배열로 받기");
		return "Hello World Array!";
	}
	
	// 경로 변수
	@GetMapping("hello/{userId}")
	// @PathVariable: 경로 변수를 메서드의 파라미터에 매핑하는 어노테이션
	// "userId": url의 userId
	// String userId: 메서드의 파라미터로 경로 변수에서 받은 값이 할당됨
	public String helloPath(@PathVariable("userId") String userId) {
		log.info("userId: {}", userId);
		return "ok";
	}
}
