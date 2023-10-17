package com.cos.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import java.util.List;


public interface ReplyRepository extends JpaRepository<Reply, Integer>{
	boolean existsByBoardId(int id);
	void deleteByBoardId(int id);
	boolean existsByUser(User user);
	List<Reply> findByUser(User user);
}
