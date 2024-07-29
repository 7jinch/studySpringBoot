package com.example.board.model.comment;

import java.time.LocalDateTime;
import com.example.board.model.board.Board;
import com.example.board.model.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @NoArgsConstructor
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성
  private Long comment_id;
  
  @Column(length=1000, nullable=false)
  private String contents;
  
  @ManyToOne
  @JoinColumn(name="member_id")
  private Member member;
  
  @ManyToOne
  private Board board;
  private LocalDateTime created_time;
}