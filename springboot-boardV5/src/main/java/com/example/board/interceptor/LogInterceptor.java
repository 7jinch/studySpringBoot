package com.example.board.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestURI =  request.getRequestURI();
//		log.info("프리핸들 실행: {}", requestURI);

		return true; // true면 다음 단계(컨트롤러)로 넘어감
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView // 뷰(템플릿)와 모델에 담은 값을 담고 있음
			) throws Exception {

//		log.info("포스트핸들 실행: {}", modelAndView);
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

//		log.info("애프터컴플리션 실행");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
