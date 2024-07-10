package com.example.board.model.member;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberLoginForm {
	@Size(min=4, max=20, message="아이디는 4자 이상 20자 이하여야 함")
	private String member_id;
	
	@Size(min=4, max=20, message="비밀번호는 4자 이상 20자 이하여야 함")
	private String password;
}
