package com.example.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ResponseViewController {

	// 방법 1. ModelAndView를 사용해서 view templates 찾기
	// ModelAndView.addObject(key, value): "data"라는 key로 "hello world"라는 value를 줌
	@GetMapping("response-view-v1")
	public ModelAndView responseViewV1() {
		String hello = "hello java";
		return new ModelAndView("response/hello").addObject("data", hello);
	}

	// 방법 2. ViewResolver를 사용해서 view templates 찾기
	// 파라미터 레벨에 선언만 해주면 스프링이 알아서 다 해줌
	@GetMapping("response-view-v2")
	public String responseViewV2(Model model) {
		log.info("model 담기 전: {}", model);
		
		String hello = "hello java";
		model.addAttribute("data", hello);

		log.info("model 담은 후: {}", model);
		return "response/hello";
	}

	// 방법 3. 리턴 타입을 void로 지정하면 url 요청값과 같은 html을 찾음
//	@GetMapping("response/hello") // 이걸 찾음
//	public void responseViewV3(Model model) {
//		log.info("model 담기 전: {}", model);
//		
//		String hello = "hello java";
//		model.addAttribute("data", hello);
//		
//		log.info("model 담은 후: {}", model);
//	}
}
