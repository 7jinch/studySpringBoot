package com.example.board.model.board;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @NoArgsConstructor
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성
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
	
	public void setHitAndCreatedTime() {
		this.hit = 0L;
		this.created_time = LocalDateTime.now();
	}
	
	// 조회수 +1
	public void addHit() {
		this.hit++;
	}
}