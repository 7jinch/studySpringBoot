package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.model.board.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired // 의존성 주입
//	private BoardRepository = new BoardRepository();
//	private BoardRepository repository; // 선언만 해도 스프링이 위 작업을 수행해 줌
	private BoardService boardService;
	
	// 메인 페이지
	@GetMapping("/")
	public String home() {
		log.info("home() 실행");
		return "redirect:/list";
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
		
		// 초기 조회수와 작성일 세팅하기
		board.setHitAndCreatedTime();
		
		log.info("{}", board);
		boardService.saveBoard(board);
		// return "index"; // 이렇게 하면 url에 /write가 남음
		return "redirect:list"; // 이렇게 하면 url이 /list가 됨
	}
	
	// 게시글 목록 페이지
	@GetMapping("list")
	public String list(Model model) {
		log.info("list() 실행");
		// 1. 리포지토리에서 데이터 가져오기
		List<Board> boards = boardService.findAll();
		log.info("글 목록: {}", boards);
		
		// 2. 모델에 담기
		model.addAttribute("boardList", boards);
		
		// 3. list.html에서 반복문으로 실제 페이지에 출력하기
		return "list";
	}
	
	// 게시글 하나 읽기
	@GetMapping("read")
	public String read(@RequestParam(value = "id", required = false) Long id, Model model) {
		Board board = boardService.findBoard(id);
		model.addAttribute(board);
		return "read";
	}
	
	// 삭제: 게시물의 패스워드와 입력한 패스워드가 같아야 삭제
	@PostMapping("delete")
	public String remove(@RequestParam("id") Long id, @RequestParam("password") String password) {
		Board board = boardService.findBoard(id);
		if(board != null) {
			if(board.getPassword().equals(password)) {
				log.info("맞음 - 삭제");
				boardService.deleteBoard(id);
			}
			log.info("안 맞음 - 삭제 안 함");
		}
		return "redirect:/list";
	}
	
	// 수정
	@GetMapping("update")
	public String updateFome(@RequestParam("id") Long id, Model model) {
		Board board = boardService.findBoard(id);
		model.addAttribute("board", board);
		return "update";
	}

	@PostMapping("update")
	public String update(@RequestParam("id") Long id, @ModelAttribute Board updateBoard) {
		boardService.editBoard(id, updateBoard);
		return "redirect:/list";
	}
}
