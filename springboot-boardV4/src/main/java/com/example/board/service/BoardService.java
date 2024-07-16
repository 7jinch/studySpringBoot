package com.example.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

  // 의존성 주입
  // 방법 1: 필드 주입 방식
  // @Autowired // setter로 의존성 주입하기
  // private BoardRepository boardRepository;

  // 방법 2: 생성자로 의존성 주입하기
  // private final BoardRepository boardRepository;
  // public BoardService(BoardRepository boardRepository) {
  // this.boardRepository = boardRepository;
  // }

  // 방법 3: 클래스 레벨에 @RequiredArgsConstructor 붙이고 필드에 final 키워드 붙이기
  private final BoardRepository boardRepository;
  private final FileRepository fileRepository;

  // 게시물 등록 1: 첨부파일이 있는 경우
  @Transactional // 혹시라도 터졌다면 롤백해라, 에러가 발생하면 insert를 안 하게 해 줌
  public void saveBoard(Board board, AttachedFile attachedFile) {
    boardRepository.save(board);
    fileRepository.save(attachedFile);
  }

  // 게시물 등록 2: 첨부파일이 없는 경우
  @Transactional // 혹시라도 터졌다면 롤백해라, 에러가 발생하면 insert를 안 하게 해 줌
  public void saveBoard(Board board) {
    boardRepository.save(board);
  }

  // 게시물 등록
  // @Transactional // 혹시라도 터졌다면 롤백해라, 에러가 발생하면 insert를 안 하게 해 줌
  // public void saveBoard(Board board, AttachedFile attachedFile) {
  // if(attachedFile != null) { // 첨부파일이 있는 경우
  // boardRepository.save(board);
  // fileRepository.save(attachedFile);
  // } else { // 첨부파일이 없는 경우
  // boardRepository.save(board);
  // }
  // }


  // 전체 게시물 조회
  public Page<Board> findAll(Pageable pageable) {
    Page<Board> page = boardRepository.findAll(pageable);
    
    return page;
  }

  // 특정 게시물 조회
  public Board findBoard(Long id) {
    // 기존에 하던 방식
    // Board board = boardRepository.findById(id).get();
    // board.addHit();
    // saveBoard(board);
    // return board;

    // board를 찾았으면 그걸 반환하고 못 찾았으면 null을 반환
    Optional<Board> optionalBoard = boardRepository.findById(id);
    Board board = optionalBoard.orElse(null);

    if (board != null) {
      board.addHit(); // 게시물 조회수 증가
      boardRepository.save(board); // 변경된 내용 저장
    }
    
    return board;
  }

  // 게시물 수정
  @Transactional // 혹시라도 터졌다면 롤백해라, 에러가 발생하면 insert를 안 하게 해 줌
  public void editBoard(Board updateBoard) {
    Board findBoard = findBoard(updateBoard.getId()); // db에서 가져올 때 스냅샷을 찍음

    findBoard.setTitle(updateBoard.getTitle());
    findBoard.setContents(updateBoard.getContents());
    findBoard.setHit(updateBoard.getHit());

    // save(): 스냅샷과 데이터가 다르면 -> pk가 없다면 생성해주고 pk가 있다면 수정해 줌
    boardRepository.save(findBoard);
  }

  // 게시물 삭제
  @Transactional // 혹시라도 터졌다면 롤백해라, 에러가 발생하면 insert를 안 하게 해 줌
  public void deleteBoard(Long id) {
    boardRepository.deleteById(id);
  }

  // 첨부파일 가져오기
  public AttachedFile findFileByBoardId(Board board) {
    AttachedFile attachedFile = fileRepository.findByBoard(board);

    return attachedFile;
  }

  public AttachedFile findFileByAttachedFileId(Long id) {
    Optional<AttachedFile> optionalAttachedFile = fileRepository.findById(id);
    AttachedFile attachedFile = optionalAttachedFile.orElse(null);

    return attachedFile;
  }

  public int getTotal() {
    return (int)boardRepository.count();
  }
}
