package com.example.mvc.controller.exception;

import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ServletExceptionController {
	
	/*
	 * 스프링에서의 예외 처리 흐름
	 * 컨트롤러에서 예외 발생 -> 인터셉터로 전달됨 -> 서블릿 -> 필터 -> was
	 * 
	 * 자바에서 예외 발생: 프로그램 종료
	 * 스프링에서 예외 발생: 프로그램이 종료되지 않고 서버는 계속 실행됨
	 * */
	
	// 오류 처리 방법 1: throw Exception
	// 스프링(화이트 라벨)이나 톰캣에서 만들어 준 페이지를 띄워줌
	// 어떤 에러가 발생했는지 다 보임
	@GetMapping("error-ex")
	public void errorEx() {
		throw new RuntimeErrorException(null, "런타임 예외 발생");
	}
	
	// 오류 처리 방법 2: response.send(상태코드, "에러 메시지")
	// response.send(상태코드, "에러 메시지")
	// 응답 객체에 예외를 담아서 보내기
	// 자바 방법임
	// html 파일을 return 해 주지 않아도 templates 폴더 아래에서 error 폴더를 찾고 그 아래에서 에러 페이지를 찾음
	@GetMapping("error-500")
	public void error500(HttpServletResponse response) throws IOException {
		// response.send(상태코드, "에러 메시지")
		// 응답 객체에 예외를 담아서 보내기
		response.sendError(500, "500 오류 발생");
	}

	@GetMapping("error-404")
	public void error404(HttpServletResponse response) throws IOException {
		// response.send(상태코드, "에러 메시지")
		// 응답 객체에 예외를 담아서 보내기
		response.sendError(404, "404 오류 발생");
	}

	@GetMapping("error-403")
	public void error403(HttpServletResponse response) throws IOException {
		// response.send(상태코드, "에러 메시지")
		// 응답 객체에 예외를 담아서 보내기
		response.sendError(403, "403 오류 발생");
	}
}
