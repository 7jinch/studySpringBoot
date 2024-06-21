package com.example.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestMappingController {
	
	@RequestMapping("hello")
	public String hello() {
		return "Hello World!";
	}
}
