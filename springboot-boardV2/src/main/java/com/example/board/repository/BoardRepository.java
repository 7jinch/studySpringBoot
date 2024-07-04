package com.example.board.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.board.model.board.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
	// JpaRepository를 상속받으면
	// 1. save, findAll, findById, deleteById 등 메서드도 상속받음
	// 2. commit까지 다 해 줌
	
	// 게시물 등록
	// save(board);
	
	// 전체 게시물 조회
	// findAll();
	
	// 특정 게시물 조회
	// findById(id);
	
	// 게시물 수정
	// save(board);
	
	// 게기물 삭제
	// deleteById(id);
}
