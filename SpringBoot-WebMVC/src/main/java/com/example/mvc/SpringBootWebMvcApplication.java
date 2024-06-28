package com.example.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.mvc.controller.RequestMappingController;

@SpringBootApplication
public class SpringBootWebMvcApplication {

	public static void main(String[] args) {
		// 실행될 때 IoC 컨테이너가 객체들을 생성하고 보관하고 있어서 아래처럼 객체 생성 코드를 작성할 필요 없음
//		RequestMappingController rmc = new RequestMappingController();
//		rmc.hello();
		
		SpringApplication.run(SpringBootWebMvcApplication.class, args);
	}
}
