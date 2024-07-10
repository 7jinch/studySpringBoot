package com.example.mvc.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.mvc.model.MemberData;
import com.example.mvc.model.exception.ErrorResult;
import com.example.mvc.model.exception.UserException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController // @Contoller 어노테이션과 @ResponseBody 어노테이션을 합친 거
// @Controller
public class RestExceptionController {
	
//	@ResponseBody
	@GetMapping("api/string")
	public String getString() {
		log.info("api/string 호출");
		return "hello world!";
	}
	
//	@GetMapping("api/members/{id}") // url 파라미터로 요청을 받음
//	public MemberData getMember(@PathVariable("id") String id) { // url 파라미터의 id를 string 타입의 id로 받음
//		log.info("아이디: {}", id);
//		MemberData memberData = new MemberData(id, "김길동", 30);
//		return memberData;
//	}
	
	@GetMapping("api/members/{id}") // url 파라미터로 요청을 받음
	public MemberData getMember(@PathVariable("id") String id) throws Exception { // url 파라미터의 id를 string 타입의 id로 받음
		log.info("아이디: {}", id);
		
		if(id.equals("error")) {
			throw new RuntimeException("잘못된 사용자");
		}
		
		if(id.equals("bad-request")) {
			throw new IllegalArgumentException("잘못된 요청");
		}
		
		if(id.equals("user-ex")) {
			throw new UserException("사용자 정의 오류");
		}
		
		if(id.equals("exception")) {
			throw new Exception("기타 예외");
		}
		
		MemberData memberData = new MemberData(id, "김길동", 30);
		return memberData;
	}
	
//	// 에러 처리 방법 1
//	@ResponseStatus(HttpStatus.BAD_REQUEST) // 상태코드를 어노테이션으로 지정함(지정하지 않으면 200 OK)
//	@ExceptionHandler
//	public ErrorResult illegalExHandle(IllegalArgumentException e) { // IllegalArgumentException 예외 잡기
//		log.info(e.getMessage());
//		return new ErrorResult("BAD-REQUEST", e.getMessage());
//	}
//	
//	// 에러 처리 방법 2
//	@ExceptionHandler
//	public ResponseEntity<ErrorResult> userExHandle(UserException e){ // UserException 잡기
//		log.info(e.getMessage());
//		ErrorResult result = new ErrorResult("USER-EX", e.getMessage());
//		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST); // 위와는 다르게 상태코드를 메서드 안에서 지정
//	}
//
//	@ExceptionHandler
//	public ResponseEntity<ErrorResult> exHandle(Exception e){ // 안 걸리진 모든 예외를 잡음
//		log.info(e.getMessage());
//		ErrorResult result = new ErrorResult("EX", e.getMessage());
//		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR); // 위와는 다르게 상태코드를 메서드 안에서 지정
//	}
}
