package com.example.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // 필드를 전부 받는 생성자
@NoArgsConstructor // 기본 생성자
public class MemberData {
	private String id;
	private String name;
	private int age;
	
	// setter, getter는 직접 구현하지 않아도 됨
}
