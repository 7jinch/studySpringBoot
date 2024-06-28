package com.example.board.repository;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.example.board.model.Board;

@Repository
public class BoardRepository {
	private Map<Long, Board> boardMap = new HashMap<>();
	
	private Long sequence = 0L; // 시퀀스 역할을 하게 될 id 초기값
	
	// 게시물 등록
	public void saveBoard(Board board) {
		board.setId(++sequence); // id 설정
		boardMap.put(board.getId(), board); // key는 board의 id, value는 board 객체
	}
	
	// 전체 게시물 조회
	// map을 list로 변환해서 반환해보기
	public List<Board> findAllBoard(){
		List<Board> list = new ArrayList<>();
		list.addAll(boardMap.values());
		// map.values() : map의 value들만 가져와서 collection 형태로 반환해 줌
		// list.addAll(): collection을 주면 list로 변환해서 리스트에 담아줌
		return list;
	}
	
	// 특정 게시물 조회
	public Board findBoard(Long id) {
		Board board = boardMap.get(id); // map은 key(board의 id)를 주면 해당하는 value(board 객체)를 줌
		return board;
	} 
	
	// 게시물 수정
	public void editBoard(Long id, Board board) {
		Board oldBoard = boardMap.get(id);
		// 수정 대상은 제목, 내용, 작성자
		if(oldBoard != null) {
			oldBoard.setTitle(board.getTitle());
			oldBoard.setContents(board.getContents());
			oldBoard.setUsername(board.getUsername());
		}
	}
	
	// 게기물 삭제
	public void deleteBoard(Long id) {
		boardMap.remove(id);
	}
}
