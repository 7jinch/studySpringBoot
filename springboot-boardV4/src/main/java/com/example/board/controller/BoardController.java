package com.example.board.controller;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.model.board.BoardUpdateForm;
import com.example.board.model.board.BoardWriteForm;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;
import com.example.board.util.FileService;
import com.example.board.util.PageNavigator;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {
  // 페이징을 위한 상수
  private final int countPerPage = 10;
  private final int pagePerGroup = 10;

  // 의존성 주입 방법 1
  // @Autowired // 필드 주입 방식임
  // private BoardRepository = new BoardRepository();
  // private BoardRepository repository; // 선언만 해도 스프링이 위 작업을 수행해 줌
  // private BoardService boardService;

  // 의존성 주입 방법 2: @RequiredArgsConstructor 클래스 레벨에 어노테이션 있어야 함
  private final BoardService boardService;
  private final FileService fileService;

  // 초기 데이터 생성
  // @PostConstruct
  // public void initData() {
  // log.info("데이터 초기화 중...");
  // for (int i = 0; i <= 100; i++) {
  // BoardWriteForm boardWriteForm = new BoardWriteForm();
  // boardWriteForm.setTitle("제목" + i);
  // boardWriteForm.setContents("내용" + i);
  //
  // Member member = new Member();
  // member.setMember_id("aaaa");
  //
  // Board board = BoardWriteForm.toBoard(boardWriteForm);
  // board.setMember(member);
  //
  // boardService.saveBoard(board);
  // }
  // }

  // 게시글 작성 페이지 이동
  @GetMapping("write")
  public String writeForm(Model model) {
    // 세션 중 loginMember라는 이름의 세션을 찾아서 loginMember라면 Member 타입 변수에 저장하기
    // log.info("writeForm() 실행");

    // 세션 체크하기: 로그인한 사람만 접근 가능
    // if(loginMember == null) {
    // return "redirect:/member/login";
    // }

    model.addAttribute("writeForm", new BoardWriteForm());

    return "board/write";
  }

  @PostMapping("write")
  public String writeForm2(
      @Validated @ModelAttribute(name = "writeForm") BoardWriteForm boardWriteForm,
      BindingResult result,
      @SessionAttribute(name = "loginMember", required = false) Member loginMember,
      @RequestParam(name = "file", required = false) MultipartFile file) {
    // log.info("writeForm2() 실행");
    // log.info("{}", boardWriteForm);

    // log.info("파일은 어떻게 생겼니: {}", file);

    // 에러가 발생하면 다시 글쓰기 페이지로 이동시키기
    if (result.hasErrors()) {

      return "board/write";
    }

    Board board = BoardWriteForm.toBoard(boardWriteForm); // 게시물 이름과 내용 세팅하면서 Board 객체를 생성해주기
    board.setMember(loginMember); // 로그인한 멤버(글 쓴 멤버 세팅)

    // 첨부파일이 있는 경우
    if (!file.isEmpty()) {
      AttachedFile attachedFile = fileService.saveFile(file);
      attachedFile.setBoard(board);

      boardService.saveBoard(board, attachedFile); // 게시글과 첨부파일을 저장

      // return "index"; // 이렇게 하면 url에 /write가 남음
      return "redirect:/board/list"; // 이렇게 하면 url이 /list가 됨
    }

    // 첨부파일이 없는 경우
    boardService.saveBoard(board); // 게시글을 저장

    // return "index"; // 이렇게 하면 url에 /write가 남음
    return "redirect:/board/list"; // 이렇게 하면 url이 /list가 됨
  }

  // 게시글 목록 페이지
  @GetMapping("list")
  public String list(@PageableDefault(size = 10) Pageable pageable,
      @RequestParam(name = "page", defaultValue = "1") int page,
      // public String list(@PageableDefault(size=10) Pageable pageable,
      Model model
  // HttpServletRequest request) {
  // @SessionAttribute(name="loginMember", required=false) Member loginMember) {
  // 세션 중 loginMember라는 이름의 세션을 찾아서 loginMember라면 Member 타입 변수에 저장하기
  ) {
    // log.info("list() 실행");
    // HttpSession session = request.getSession(false);
    // if(session == null) {
    // return "redirect:/member/login";
    // }

    // 세션 체크하기: 로그인한 사람만 접근 가능
    // if(loginMember == null) {
    // return "redirect:/member/login";
    // }

    // 1. 리포지토리에서 데이터 가져오기
    Page<Board> boards = boardService.findAll(pageable);
    // log.info("글 목록: {}", boards);

    // 2. 모델에 담기
    model.addAttribute("boardList", boards);

    // 3. list.html에서 반복문으로 실제 페이지에 출력하기

    // 전체 글 수
    int totalPostCount = boardService.getTotal();
    // log.info("전체 글 수: {}", totalPostCount);

    // 전체 페이지 수
    int totalPageCount = pageable.getPageSize();
    // log.info("전체 페이지 수: {}", totalPageCount);

    // 4. 페이징
    PageNavigator navi =
        new PageNavigator(countPerPage, pagePerGroup, page, totalPostCount, totalPageCount);
    model.addAttribute("navi", navi);
    
    return "board/list";
  }

  // 게시글 하나 읽기
  @GetMapping("read")
  public String read(@RequestParam(value = "id", required = false) Long id, Model model) {

    // if(loginMember == null) {
    // return "redirect:/member/login";
    // }

    Board board = boardService.findBoard(id);

    if (board == null) {
      return "notFound";
    }

    model.addAttribute("board", board);

    // 첨부파일이 있는 지 찾기
    AttachedFile attachedFile = boardService.findFileByBoardId(board);
    // log.info("첨부파일: {}", attachedFile);

    if (attachedFile != null) {
      model.addAttribute("file", attachedFile);
    }

    return "board/read";
  }

  // 삭제: 게시물의 패스워드와 입력한 패스워드가 같아야 삭제
  @GetMapping("delete")
  public String remove(@RequestParam(name = "id", required = false) Long id,
      @SessionAttribute(name = "loginMember", required = false) Member loginMember) {
    // log.info("넘어온 id: {}", id);

    // 세션으로 로그인 상태인지 체크
    if (loginMember == null) {
      return "redirect:/member/login";
    }

    // 글이 존재하는지 체크 or 삭제 권한이 있는지 체크
    Board board = boardService.findBoard(id);
    if (board == null || loginMember.getMember_id() == board.getMember().getMember_id()) {
      return "noPermission";
    }
    // log.info("찾은 삭제하려는 board의 id: {}", board.getBoard_id());

    boardService.deleteBoard(id);

    return "redirect:/board/list";
  }

  // 수정
  @GetMapping("update")
  public String updateFome(@RequestParam(name = "id", required = false) Long id, Model model,
      @SessionAttribute(name = "loginMember", required = false) Member loginMember) {

    if (loginMember == null) {
      return "redirect:/member/login";
    }

    Board board = boardService.findBoard(id);
    if (board == null || !board.getMember().getMember_id().equals(loginMember.getMember_id())) {
      return "redirect:/board/list";
    }

    BoardUpdateForm boardUpdateForm = Board.toUpdateForm(board);

    model.addAttribute("boardUpdateForm", boardUpdateForm);

    return "board/update";
  }

  @PostMapping("update")
  public String update(@Validated @ModelAttribute BoardUpdateForm boradUpdateBoard,
      BindingResult result) {

    if (result.hasErrors()) {
      return "board/update";
    }

    Board updateBoard = BoardUpdateForm.toBoard(boradUpdateBoard);
    boardService.editBoard(updateBoard);

    return "redirect:/board/list";
  }

  // 다운로드
  @Value("${file.upload.path}")
  private String uploadPath;

  @GetMapping("download/{id}")
  public ResponseEntity<Resource> download(@PathVariable(name = "id") Long id)
      throws MalformedURLException {
    // 1. 첨부파일 아이디로 첨부파일을 가져오기
    AttachedFile attachedFile = boardService.findFileByAttachedFileId(id);

    // 2. 다운로드하려는 파일의 절대경로 값을 만들기
    String fullPath = uploadPath + "/" + attachedFile.getSaved_filename();

    UrlResource resource = new UrlResource("file:" + fullPath);

    // 3. 한글 파일명이 깨지지 않도록 파일명을 UTF_8로 인코딩하기
    String encodingFileName =
        UriUtils.encode(attachedFile.getOriginal_filename(), StandardCharsets.UTF_8);

    // 4. 응답 헤더에 담을 Content Disposition 값을 생성
    String contentDisposition = "attachment; filename=\"" + encodingFileName + "\"";


    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
        .body(resource);
  }
}
