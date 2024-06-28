package com.example.thymeleaf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode를 자동으로 생성해 줌 
@NoArgsConstructor // 기본 생성자를 자동 생성
@AllArgsConstructor // 필드 전체를 받는 생성자를 자동 생성
public class MemberData {
	private String id;
	private String name;
	private int age;
}
