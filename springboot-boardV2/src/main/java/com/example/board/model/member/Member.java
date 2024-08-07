package com.example.board.model.member;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Member {
	@Id
	@Column(length=20)
	private String member_id;
	
	@Column(length=20, nullable=false)
	private String password;
	
	@Column(length=50, nullable=false)
	private String name;
	
	@Column(length=10)
	@Enumerated(EnumType.STRING)
	private GenderType gender;
	
	private LocalDate birth;
	
	@Column(length=100)
	private String email;
}
