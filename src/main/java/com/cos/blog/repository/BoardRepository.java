package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;
import com.cos.blog.model.Member;
import java.util.List;




public interface BoardRepository extends JpaRepository<Board, Integer> {
	/* Page<Board> findByTitleContaining(String keyword1,Pageable pageable); */
	Page<Board> findByTitleContainingOrContentContaining(String keyword1,String keyword2,Pageable pageable);
	boolean existsByMember(Member user);
	List<Board> findByMember(Member user);
}