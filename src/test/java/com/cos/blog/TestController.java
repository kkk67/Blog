package com.cos.blog;

import java.awt.print.Pageable;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.Board;
import com.cos.blog.model.Member;
import com.cos.blog.repository.BoardRepository;

@SpringBootTest
@Transactional
public class TestController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	
	@Test
	public void pageTest() {
		PageRequest pageable = PageRequest.of(0, 10);
		for(int i=0; i<100; i++) {
			Board board = new Board();
			board.setTitle("test" + i);
			board.setContent("test" + i);
			board.setCount(0);
			boardRepository.save(board);
		}
		
		Page<Board> result = boardRepository.findAll(pageable);
		
		System.out.println(result);
		
		System.out.println("Total Pages : " + result.getTotalPages());
		System.out.println("Total Elements : " + result.getTotalElements());
		System.out.println("Size : " + result.getSize());
		System.out.println("Number : " + result.getNumber());
		System.out.println("hasNext : "+result.hasNext());
		System.out.println("is First : "+result.isFirst());

		for(int i=0; i< result.getTotalPages(); i++ ) {
			
		}
		
	}
}
