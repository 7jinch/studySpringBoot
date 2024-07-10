package com.example.board.model.member;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*	유효성 검사 어노테이션
 *	@Size: 문자열의 길이
 *	@NotNull: null만 금지
 *	@NotEmpty: null, ""만 금지
 *	@NotBlank: null, "", " "다 금지
 *	@Past: 과거 날짜만 선택 가능
 *	@PastOrPresent: 과거, 오늘 선택 가능
 *	@Future: 미래 날짜만 선택 가능
 *	@Pattern: 정규표현식 사용
 *	@Max: 최대값
 *	@Min: 최소값
 *	@Valid: 해당 객체의 validation을 실행하고 싶을 때
 * */
@Getter @Setter @ToString
public class MemberJoinForm {
	@Size(min=4, max=20, message="아이디는 4자 이상 20자 이하여야 함")
	private String member_id;
	
	@Size(min=4, max=20, message="비밀번호는 4자 이상 20자 이하여야 함")
	private String password;
	
	@NotBlank
	private String name;
	
	@NotNull(message="선택하세요")
	private GenderType gender;
	
	@Past
	@NotNull(message="입력없음")
	private LocalDate birth;
	
	private String email;
	
	// MemberJoinForm을 Member로 변환
	public static Member toMember(MemberJoinForm memberJoinForm) {
		// MemberJoinForm와 Member는 형태는 같지만
		// MemberJoinForm은 유효성 검사를 위한 자바 클래스이고 Member는 엔티티라서 바로 못 넣기 때문에
		// 아래처럼 Member 인스턴스를 생성해서 넣어줘야 함
		Member member = new Member();
		member.setMember_id(memberJoinForm.getMember_id());
		member.setPassword(memberJoinForm.getPassword());
		member.setName(memberJoinForm.getName());
		member.setGender(memberJoinForm.getGender());
		member.setBirth(memberJoinForm.getBirth());
		member.setEmail(memberJoinForm.getEmail());
		
		return member;
	}
}
