package com.example.mvc.model;

import lombok.Data;

@Data
public class MemberData {
	private String id;
	private String name;
	private int age;
	
	// setter, getter는 직접 구현하지 않아도 됨
}
