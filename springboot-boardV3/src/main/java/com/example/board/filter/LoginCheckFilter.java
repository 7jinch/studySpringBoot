package com.example.board.filter;

import java.io.IOException;

import org.springframework.util.PatternMatchUtils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, // 세션 데려오는 친구
						 ServletResponse response, // 쿠키 데려오는 친구
						 FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest httpRequest = (HttpServletRequest)request; // 세션이랑 uri 가져오려고 형변환
		HttpServletResponse httpResponse = (HttpServletResponse)response; // 리다이렉트해 주는 HttpServletResponse를 사용하려면 형변환
		
		try {
			log.info("로그인체크필터 실행");
			String requestURI = httpRequest.getRequestURI(); // uri 가져오기
//			String id = httpRequest.getParameter("id"); // 컨트롤러로 가기전에 파라미터를 가져와서 체크하고 거를 수도 있음
//			log.info("아이디: {}", id);
			
			if(isLoginCheckPath(requestURI)) {
				HttpSession session = httpRequest.getSession(false); // 세션 가져오기
				
				// 로그인하지 않은 상태인지 체크
				if(session == null || session.getAttribute("loginMember") == null) {
					log.info("로그인하지 않은 상태");
					
					// return "redirect:/member/login"; // 이렇게는 안 됨
					httpResponse.sendRedirect("/member/login"); // HttpServletResponse로 리다이렉트하기
					
					return;
				}
			}
			chain.doFilter(request, response);
		} catch(Exception e) {
			
		} finally {
			log.info("로그인체크필터 종료");
		}
	}
	
	private boolean isLoginCheckPath(String requestURI) {
		// 로그인하지 않아도 들어갈 수 있는 경로들
		String[] whiteList = {
				"/", // 최상위경로
				"/member/login", // 로그인 페이지
				"/member/join", // 회원가입 페이지
				"/default.css",
				"/favicon.ico"
		};
		
		boolean checked = !PatternMatchUtils.simpleMatch(whiteList, requestURI);
		return checked; // 체크 안 해도 됨
	}
}
