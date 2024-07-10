package com.example.board.model.member;

/* enum
 * 서로 연관된 상수들의 집합
 * */
public enum GenderType {
	MALE("남성"), // <- () 안에 있는 문자열이 desc임
	FEMALE("여성");
	
	private String desc;
	
	private GenderType(String desc) {
		this.desc = desc;
	}
	
	public String getDesc() {
		return this.desc;
	}
}
