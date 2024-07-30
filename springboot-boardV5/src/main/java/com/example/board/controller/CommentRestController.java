package com.example.board.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.example.board.model.board.Board;
import com.example.board.model.comment.Comment;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.service.BoardService;
import com.example.board.util.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("comment")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:9000") // 허용할 도메인
public class CommentRestController {
  private final BoardService boardService;
  private final BoardRepository boardRepository;
  private final CommentRepository commentRepository;

  // 댓글 등록
  @PostMapping("{board_id}") // 경로변수로 받기
  public ResponseEntity<String> createComment(@PathVariable("board_id") Long board_id,
      @RequestParam("comment") String commentText,
      // @ModelAttribute Comment comment,
      @SessionAttribute("loginMember") Member loginMember) {
    // 댓글 객체 생성하기
    Comment comment = new Comment();

    // 어떤 내용인지
    comment.setContents(commentText);

    // 어떤 글의 댓글이지
    Board board = boardRepository.findById(board_id).get();
    comment.setBoard(board);

    // 누가 썼지
    comment.setMember(loginMember);

    // 언제 썼지
    comment.setCreated_time(LocalDateTime.now());

    // log.info("comment: {}", comment);

    // db에 저장하기
    commentRepository.save(comment);

    return ResponseEntity.ok("등록 성공");
  }

  // 댓글 읽기
  @GetMapping("{board_id}/{comment_id}")
  public ResponseEntity<Comment> findComment(@PathVariable("board_id") Long board_id,
      @PathVariable("comment_id") Long comment_id) {
    Comment comment = null;
    return ResponseEntity.ok(comment);
  }

  // 댓글 목록
  @GetMapping("{board_id}")
  public ResponseEntity<List<Comment>> findComments(
      @SessionAttribute("loginMember") Member loginMember,
      @PathVariable("board_id") Long board_id) {
    
    Board board = boardService.findBoard(board_id);
    // Board board = new Board();
    // board.setId(board_id);
    List<Comment> comments = commentRepository.findByBoard(board); // db에서 가져오기
    
    // 수정, 삭제 권한 여부 검사
    if(comments != null && comments.size() > 0) {
      for(Comment comment : comments) {
        if(comment.getMember().getMember_id().equals(loginMember.getMember_id())) {
          comment.setWriter(true);
        }
      }
    }
    
    return ResponseEntity.ok(comments);
  }

  // 댓글 수정
  @PutMapping("{board_id}/{comment_id}")
  public ResponseEntity<Comment> updateComment(@SessionAttribute("loginMember") Member loginMember,
      @PathVariable("board_id") Long board_id, @PathVariable("comment_id") Long comment_id,
      @ModelAttribute Comment comment) {
    
    // 수정 권한이 있는지 검사
    Comment findComment = commentRepository.findById(comment_id).get();
    if(findComment.getMember().getMember_id().equals(loginMember.getMember_id())) {
      findComment.setContents(comment.getContents());
      commentRepository.save(findComment);
    }

    return ResponseEntity.ok(findComment); // 수정된 객체를 리턴
  }

  // 댓글 삭제
  @DeleteMapping("{board_id}/{comment_id}")
  public ResponseEntity<String> deleteComment(@SessionAttribute("loginMember") Member loginMember,
      @PathVariable("board_id") Long board_id, @PathVariable("comment_id") Long comment_id) {
    
    // 삭제 권한이 있는지 검사
    Comment findComment = commentRepository.findById(comment_id).get();
    if(findComment.getMember().getMember_id().equals(loginMember.getMember_id())) {
      commentRepository.deleteById(comment_id);
    }
    
    return ResponseEntity.ok("삭제 성공");
  }
}
