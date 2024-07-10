package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.board.model.board.Board;
import com.example.board.model.board.BoardUpdateForm;
import com.example.board.model.board.BoardWriteForm;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("board")
public class BoardController {
	@Autowired // 의존성 주입
//	private BoardRepository = new BoardRepository();
//	private BoardRepository repository; // 선언만 해도 스프링이 위 작업을 수행해 줌
	private BoardService boardService;
	
	// 게시글 작성 페이지 이동
	@GetMapping("write")
	public String writeForm(Model model,
							@SessionAttribute(name="loginMember", required=false) Member loginMember) {
							// 세션 중 loginMember라는 이름의 세션을 찾아서 loginMember라면 Member 타입 변수에 저장하기
		log.info("writeForm() 실행");
		
		// 세션 체크하기: 로그인한 사람만 접근 가능
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		model.addAttribute("writeForm", new BoardWriteForm());
		return "board/write";
	}
	
	@PostMapping("write")
	public String writeForm2(@Validated @ModelAttribute(name="writeForm") BoardWriteForm boardWriteForm,
							 BindingResult result,
							 @SessionAttribute(name="loginMember") Member loginMember) {
		log.info("writeForm2() 실행");
		log.info("{}", boardWriteForm);
		
		// 에러가 발생하면 다시 글쓰기 페이지로 이동시키기
		if(result.hasErrors()) {
			
			return "board/write";
		}
		
		Board board = BoardWriteForm.toBoard(boardWriteForm); // 게시물 이름과 내용 세팅하면서 Board 객체를 생성해주기
		board.setMember(loginMember); // 로그인한 멤버(글 쓴 멤버 세팅)
		
		boardService.saveBoard(board);
		
		// return "index"; // 이렇게 하면 url에 /write가 남음
		return "redirect:/board/list"; // 이렇게 하면 url이 /list가 됨
	}
	
	// 게시글 목록 페이지
	@GetMapping("list")
	public String list(Model model,
					   // HttpServletRequest request) {
					   @SessionAttribute(name="loginMember", required=false) Member loginMember) {
					   // 세션 중 loginMember라는 이름의 세션을 찾아서 loginMember라면 Member 타입 변수에 저장하기
		log.info("list() 실행");
//		HttpSession session = request.getSession(false);
//		if(session == null) {
//			return "redirect:/member/login";
//		}

		// 세션 체크하기: 로그인한 사람만 접근 가능
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		// 1. 리포지토리에서 데이터 가져오기
		List<Board> boards = boardService.findAll();
		log.info("글 목록: {}", boards);
		
		// 2. 모델에 담기
		model.addAttribute("boardList", boards);
		
		// 3. list.html에서 반복문으로 실제 페이지에 출력하기
		return "board/list";
	}
	
	// 게시글 하나 읽기
	@GetMapping("read")
	public String read(@RequestParam(value = "id", required = false) Long id,
					   Model model,
					   @SessionAttribute(name="loginMember", required=false) Member loginMember) {
		
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		Board board = boardService.findBoard(id);
		
		if(board == null) {
			return "notFound";
		}
		
		model.addAttribute("board", board);
		return "board/read";
	}
	
	// 삭제: 게시물의 패스워드와 입력한 패스워드가 같아야 삭제
	@GetMapping("delete")
	public String remove(@RequestParam(name="id", required=false) Long id,
						 @SessionAttribute(name="loginMember", required=false) Member loginMember) {
//		log.info("넘어온 id: {}", id);

		// 세션으로 로그인 상태인지 체크
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		// 글이 존재하는지 체크 or 삭제 권한이 있는지 체크
		Board board = boardService.findBoard(id);
		if(board == null || loginMember.getMember_id() == board.getMember().getMember_id()) {
			return "noPermission";
		}
//		log.info("찾은 삭제하려는 board의 id: {}", board.getBoard_id());
		
		boardService.deleteBoard(id);
		return "redirect:/board/list";
	}
	
	// 수정
	@GetMapping("update")
	public String updateFome(@RequestParam(name="id", required=false) Long id,
							 Model model,
							 @SessionAttribute(name="loginMember", required=false) Member loginMember) {
		
		if(loginMember == null) {
			return "redirect:/member/login";
		}
		
		Board board = boardService.findBoard(id);
		if(board == null || !board.getMember().getMember_id().equals(loginMember.getMember_id())) {
			return "redirect:/board/list";
		}
		
		BoardUpdateForm boardUpdateForm = Board.toUpdateForm(board);
		
		model.addAttribute("boardUpdateForm", boardUpdateForm);
		
		return "board/update";
	}

	@PostMapping("update")
	public String update(@Validated @ModelAttribute BoardUpdateForm boradUpdateBoard,
						 BindingResult result) {
		
		if(result.hasErrors()) {
			return "board/update";
		}
		
		Board updateBoard = BoardUpdateForm.toBoard(boradUpdateBoard);
		boardService.editBoard(updateBoard);
		return "redirect:/board/list";
	}
}
