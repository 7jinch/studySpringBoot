package com.example.board.model.board;

import java.time.LocalDateTime;

import com.example.board.model.member.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	private Long board_id;
	private String title;
	private String contents;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
	
	private Long hit;
	private LocalDateTime created_time;
	
	// 조회수 +1
	public void addHit() {
		this.hit++;
	}
	
	public static BoardUpdateForm toUpdateForm(Board board) {
		BoardUpdateForm boardUpdateForm = new BoardUpdateForm();
		
		boardUpdateForm.setBoard_id(board.getBoard_id());
		boardUpdateForm.setTitle(board.getTitle());
		boardUpdateForm.setContents(board.getContents());
		boardUpdateForm.setMember(board.getMember());
		boardUpdateForm.setHit(board.getHit());
		boardUpdateForm.setCreated_time(board.getCreated_time());
		
		return boardUpdateForm;
	}
}