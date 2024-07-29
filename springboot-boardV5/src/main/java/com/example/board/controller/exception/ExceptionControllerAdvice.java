package com.example.board.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.board.model.exception.ErrorResult;
import com.example.board.model.exception.UserException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice // 프로젝트 전역으로 에러 처리
//@RestControllerAdvice(value={
//		"com.example.mvc.controller.exception.RextExceptionController" // 이 컨트롤러에서만 사용 가능하도록 지정할 수도 있음
//})
public class ExceptionControllerAdvice {
	// 에러 처리 방법 1
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 상태코드를 어노테이션으로 지정함(지정하지 않으면 200 OK)
	@ExceptionHandler
	public ErrorResult illegalExHandle(IllegalArgumentException e) { // IllegalArgumentException 예외 잡기
		log.info(e.getMessage());
		return new ErrorResult("BAD-REQUEST", e.getMessage());
	}
	
	// 에러 처리 방법 2
	@ExceptionHandler
	public ResponseEntity<ErrorResult> userExHandle(UserException e){ // UserException 잡기
		log.info(e.getMessage());
		ErrorResult result = new ErrorResult("USER-EX", e.getMessage());
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST); // 위와는 다르게 상태코드를 메서드 안에서 지정
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResult> exHandle(Exception e){ // 안 걸리진 모든 예외를 잡음
		log.info(e.getMessage());
		ErrorResult result = new ErrorResult("EX", e.getMessage());
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR); // 위와는 다르게 상태코드를 메서드 안에서 지정
	}
}
