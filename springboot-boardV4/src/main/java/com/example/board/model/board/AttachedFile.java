package com.example.board.model.board;

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
@Getter @Setter @ToString
@NoArgsConstructor
public class AttachedFile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long attachedFile_id;

  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board board;

  private String original_filename;
  private String saved_filename;
  private Long file_size;
  
  public AttachedFile(String original_filename, String saved_filename, Long file_size) {
    super();
    this.original_filename = original_filename;
    this.saved_filename = saved_filename;
    this.file_size = file_size;
  }
}
