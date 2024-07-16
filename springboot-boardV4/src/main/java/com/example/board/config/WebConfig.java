package com.example.board.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.board.filter.LogFilter;
import com.example.board.filter.LoginCheckFilter;
import com.example.board.interceptor.LogInterceptor;
import com.example.board.interceptor.LoginCheckInterceptor;

import jakarta.servlet.Filter;

@Configuration
public class WebConfig implements WebMvcConfigurer { // 인터셉터는 WebMvcConfigurer라는 인터페이스의 구현이 필요함
	
	private String[] excludePaths = {
			"/", "/member/join", "/member/login", "/member/logout",
			"/*.css", "/*.js", "/*.ico", "/error"
	};
	
//	@Bean
	FilterRegistrationBean<Filter> logFilter(){
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		
		// 동록할 필터 지정
		filterRegistrationBean.setFilter(new LogFilter());
		
		// 필터 순서 지정
		// 낮을수록 먼저 실행됨
		filterRegistrationBean.setOrder(1);
		
		// 필터를 적용할 url 패턴 지정
		filterRegistrationBean.addUrlPatterns("/*"); // 최상위경로 하위로 들어오는 모든 경로에 필터를 적용
		
		return filterRegistrationBean;
	}
	
//	@Bean
	FilterRegistrationBean<Filter> loginCheckFilter(){
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		
		// 동록할 필터 지정
		filterRegistrationBean.setFilter(new LoginCheckFilter());
		
		// 필터 순서 지정
		// 낮을수록 먼저 실행됨
		filterRegistrationBean.setOrder(2);
		
		// 필터를 적용할 url 패턴 지정
		filterRegistrationBean.addUrlPatterns("/*"); // 최상위경로 하위로 들어오는 모든 경로에 필터를 적용
		
		return filterRegistrationBean;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor()) // 인터셉터 등록
				.order(1) // 인터셉터 호출 순서 지정
				.addPathPatterns("/**") // 인터셉터를 적용한 url 패턴 지정
				.excludePathPatterns(excludePaths); // 인터셉터에서 제외할 url 패턴 지정

		registry.addInterceptor(new LoginCheckInterceptor()) // 인터셉터 등록
		.order(2) // 인터셉터 호출 순서 지정
		.addPathPatterns("/**") // 인터셉터를 적용한 url 패턴 지정
		.excludePathPatterns(excludePaths); // 인터셉터에서 제외할 url 패턴 지정
	}
}
