package com.example.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.test.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
