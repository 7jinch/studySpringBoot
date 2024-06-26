package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	@Autowired
//	private BoardRepository = new BoardRepository();
	private BoardRepository repository; // 선언만 해도 스프링이 위 작업을 수행해 줌
	
	// 초기 데이터 생성
	@PostConstruct
	// 메서드는 반드시 void 타입이어야 함
	// 객체와 의존성 주입(DI)이 모두 완료된 상태에 호출되므로
	// 필드 초기화 및 생성자 호출 등의 과정이 모두 끝난 상태이고
	// 해당 빈이 필요로 하는 모든 의존성이 설정되어 있음(즉, @Autowired나 다른 DI 메커니즘을 통해 주입받은 필드나 메서드 인자들에 대한 초기화가 완료된 상태)
	public void initData() {
		repository.saveBoard(new Board("제목 1", "내용 1", "손오공", "1234"));
		repository.saveBoard(new Board("제목 2", "내용 2", "손흥민", "1234"));
		repository.saveBoard(new Board("제목 3", "내용 3", "손고쿠 블루", "1234"));
	}
	
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
		
		// 초기 조회수와 작성일 세팅하기
		board.setHitAndCreatedTime();
		
		log.info("{}", board);
		repository.saveBoard(board);
		// return "index"; // 이렇게 하면 url에 /write가 남음
		return "redirect:list"; // 이렇게 하면 url이 /list가 됨
	}
	
	// 게시글 목록 페이지
	@GetMapping("list")
	public String list(Model model) {
		log.info("list() 실행");
		// 1. 리포지토리에서 데이터 가져오기
		List<Board> boardList = repository.findAllBoard();
//		log.info("글 목록: {}", boardList);
		
		// 2. 모델에 담기
		model.addAttribute("boardList", boardList);
		
		// 3. list.html에서 반복문으로 실제 페이지에 출력하기
		return "list";
	}
	
	// 게시글 하나 읽기
	@GetMapping("read")
	public String read(@RequestParam(value = "id", required = false) Long id, Model model) {
		Board board = repository.findBoard(id);
		board.addHit();
		model.addAttribute(board);
		return "read";
	}
	
	// 삭제: 게시물의 패스워드와 입력한 패스워드가 같아야 삭제
	@PostMapping("delete")
	public String remove(@RequestParam("id") Long id, @RequestParam("password") String password) {
//		log.info("id: {}", id);
//		log.info("password: {}", password);
		Board board = repository.findBoard(id);
		if(board != null) {
			if(board.getPassword().equals(password)) {
				log.info("맞음 - 삭제");
				repository.deleteBoard(id);
			}
		}
		return "redirect:/list";
	}
	
	// 수정
	@GetMapping("update")
	public String updateFome(@RequestParam("id") Long id, Model model) {
		Board board = repository.findBoard(id);
		model.addAttribute("board", board);
		return "update";
	}

	@PostMapping("update")
	public String update(@RequestParam("id") Long id, @ModelAttribute Board updateBoard) {
		Board findBoard = repository.findBoard(id);
		if(findBoard != null && findBoard.getPassword().equals(updateBoard.getPassword())) {
			log.info("맞음 - 수정");
			repository.editBoard(id, updateBoard);
		}
		return "redirect:/list";
	}
}
