package com.example.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.model.board.Board;
import com.example.board.repository.BoardRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {
	
	// 의존성 주입
//	@Autowired // setter로 의존성 주입하기
//	private BoardRepository boardRepository;
	
	// 생성자로 의존성 주입하기
	private final BoardRepository boardRepository;
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	// 게시물 등록
	@Transactional // 혹시라도 터졌다면 롤백해라, 에러가 발생하면 insert를 안 하게 해 줌
	public void saveBoard(Board board) {
		boardRepository.save(board);
	}
	
	// 전체 게시물 조회
	public List<Board> findAll(){
		List<Board> list = boardRepository.findAll();
		return list;
	}

	// 특정 게시물 조회
	public Board findBoard(Long id) {
		// 기존에 하던 방식
//		Board board = boardRepository.findById(id).get();
//		board.addHit();
//		saveBoard(board);
//		return board;
		
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
		Board findBoard = findBoard(updateBoard.getBoard_id()); // db에서 가져올 때 스냅샷을 찍음
		
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
}
