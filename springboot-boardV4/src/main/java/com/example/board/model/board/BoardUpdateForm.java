package com.example.board.model.board;

import java.time.LocalDateTime;

import com.example.board.model.member.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BoardUpdateForm {
	private Long board_id;
	@NotBlank // 빈 칸 금지
	private String title;
	@NotBlank // 빈 칸 금지
	private String contents;
	private Member member;
	private Long hit;
	private LocalDateTime created_time;
	private boolean fileRemoved;
	
	public static Board toBoard(BoardUpdateForm boardUpdateForm) {
		Board board = new Board();
		
		board.setId(boardUpdateForm.getBoard_id());
		board.setTitle(boardUpdateForm.getTitle());
		board.setContents(boardUpdateForm.getContents());
		board.setMember(boardUpdateForm.getMember());
		board.setHit(boardUpdateForm.getHit());
		board.setCreated_time(LocalDateTime.now());

		return board;
	}
}
