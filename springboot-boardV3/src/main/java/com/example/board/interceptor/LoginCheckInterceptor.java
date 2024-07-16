package com.example.board.interceptor;

import java.util.Enumeration;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String requestURI = request.getRequestURI(); // 요청이 들어온 uri 받아오기
		HttpSession session = request.getSession(false); // 세션 받아오기
		
		if(session == null || session.getAttribute("loginMember") == null) {
			log.info("로그인하지 않은 사용자");

			Enumeration<String> parameterNames = request.getParameterNames(); // 이 친구는 파라미터들을 다 가지고 있음
			
			StringBuffer stringBuffer = new StringBuffer();
			
			while(parameterNames.hasMoreElements()) { // 파라미터들을 순회하면서
				String parameterName = parameterNames.nextElement(); // 하나씩 조회하기
				stringBuffer.append(parameterName +  "=" + request.getParameter(parameterName) + "&26");
				// board/read?id=2에서 id=2를 만들거임
			}
			
			// 서블릿 방식 리다이렉트임
			response.sendRedirect("/member/login?redirectURL=" + requestURI + "?" + stringBuffer.toString()); // 해당 url로 리다이렉트
			return false; // false는 컨트롤러로 못 넘어감
		}
		
		return true; // true면 다음 단계(컨트롤러)로 넘어감
	}
}
