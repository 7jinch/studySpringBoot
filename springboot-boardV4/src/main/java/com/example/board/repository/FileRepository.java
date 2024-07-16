package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.board.model.board.AttachedFile;
import com.example.board.model.board.Board;

public interface FileRepository extends JpaRepository<AttachedFile, Long> {

  // 공통 메서드(find, save 등) 외 커스텀 메서드는 Repository 파일에서 추상 메서드로 만들어줘야 함
  // 구현은 할 필요 없음
  AttachedFile findByBoard(Board board);

}
