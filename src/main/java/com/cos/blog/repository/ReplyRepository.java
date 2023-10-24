package com.cos.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.Member;
import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	boolean existsByBoardId(int id);
	void deleteByBoardId(int id);
	boolean existsByMember(Member user);
	List<Reply> findByMember(Member user);
}
