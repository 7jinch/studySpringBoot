package com.example.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired
//	private BoardRepository = new BoardRepository();
	private BoardRepository repository; // 선언만 해도 스프링이 위 작업을 수행해 줌
	
	// 메인 페이지
	@GetMapping("/")
	public String home() {
		log.info("home() 실행");
		return "index";
	}
	
	// 게시글 작성 페이지 이동
	@GetMapping("write")
	public String writeFomr() {
		log.info("writeForm() 실행");
		return "write";
	}
	
	@PostMapping("write")
	public String writeFomr2(@ModelAttribute Board board) {
		log.info("writeForm2() 실행");
		log.info("{}", board);
		repository.saveBoard(board);
		return "redirect:/";
	}
	
	// 게시글 목록 페이지
	@GetMapping("list")
	public String list() {
		log.info("list() 실행");
		// 1. 리포지토리
		// 2. 모델에 담기
		// 3. list.html에서 반복문으로 실제 페이지에 출력하기
		return "list";
	}
}
