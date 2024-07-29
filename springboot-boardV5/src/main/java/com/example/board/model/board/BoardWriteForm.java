package com.example.board.model.board;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class BoardWriteForm {
	@NotBlank // 빈 칸 금지
	private String title;
	@NotBlank // 빈 칸 금지
	private String contents;
	
	public static Board toBoard(BoardWriteForm boardWriteForm) {
		Board board = new Board();
		
		board.setTitle(boardWriteForm.getTitle());
		board.setContents(boardWriteForm.getContents());
		board.setHit(0L);
		board.setCreated_time(LocalDateTime.now());

		return board;
	}
}
