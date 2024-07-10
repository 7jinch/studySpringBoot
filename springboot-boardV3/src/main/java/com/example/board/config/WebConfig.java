package com.example.board.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.board.filter.LogFilter;

import jakarta.servlet.Filter;

@Configuration
public class WebConfig {
	@Bean
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
}
