package com.example.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.model.board.Board;
import com.example.board.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {
	
	// 의존성 주입
	@Autowired // 의존성 주입
	private BoardRepository boardRepository;
	
	// 게시물 등록
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
		Board board = boardRepository.findById(id).get();
		board.addHit();
		saveBoard(board);
		return board;
		
		// board를 찾았으면 그걸 반환하고 못 찾았으면 null을 반환
//		Optional<Board> board = boardRepository.findById(id);
//		return board.orElse(null);
	} 
	
	// 게시물 수정
	public void editBoard(Long id, Board updateBoard) {
		Board findBoard = findBoard(id);
		
		if(findBoard != null && findBoard.getPassword().equals(updateBoard.getPassword())) {
			log.info("맞음 - 수정");
			findBoard.setTitle(updateBoard.getTitle());
			findBoard.setContents(updateBoard.getContents());
			findBoard.setUsername(updateBoard.getUsername());
		} else {
			log.info("안 맞음 - 삭제 안 함");
		}
		
		// 위의 saveBoard 메서드의 save()에서는 id가 없기 때문에 그냥 board를 생성함
		// 이 editBoard 메서드의 save()는 스냅샷을 찍을 떄 id가 있고 값이 달라졌다면 기존의 board는 수정함
		boardRepository.save(findBoard);
	}
	
	// 게시물 삭제
	public void deleteBoard(Long id) {
		boardRepository.deleteById(id);
	}
}
