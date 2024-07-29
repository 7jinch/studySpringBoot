package com.example.board.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		log.info("로그필터의 두필터 실행");
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String requestURI = httpRequest.getRequestURI();
		
//		log.info("requestURI: {}", requestURI);
		
		chain.doFilter(request, response); // 필터가 그냥 받자마자 아무 작업을 하지 않고 다음으로 바로 넘겨줌
	}
	
	// init: 서버가 시작될 때
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("로그 필터 init");
	}
	
	// destory: 서버가 종료될 때
	@Override
	public void destroy() {
		log.info("로그 필터 destory");
	}
}
