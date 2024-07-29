package com.example.board.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.board.model.board.Board;
import com.example.board.model.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByBoard(Board board);

}
