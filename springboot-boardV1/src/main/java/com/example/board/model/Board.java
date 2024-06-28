package com.example.board.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Board {
	private Long id;
	private String title;
	private String contents;
	private String username;
	private String password;
	private Long hit;
	private LocalDateTime created_time;
	
	// id는 자동 생성되어야 함
	public Board(String title, String contents, String username, String password) {
		super();
		this.title = title;
		this.contents = contents;
		this.username = username;
		this.password = password;
		this.hit = 0L; // 생성자가 호출되었을 때(처음 생성되었을 때)의  조회수는 0
		this.created_time = LocalDateTime.now(); // 생성자가 호출되었을 때(처음 생성되었을 때)의 생성 시간
	}
}
