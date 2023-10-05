package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	/*
	 * @Bean public PageableHandlerMethodArgumentResolverCustomizer customize() {
	 * return p -> { p.setOneIndexedParameters(true); }; }
	 */
	
	//@AuthenticationPrincipal PrincipalDetail principal
	//@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC)Pageable pageable
	@GetMapping({"/",""})
	public String index(Model model,@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC)Pageable pageable) {
		int Total = boardService.글목록(pageable).getTotalPages();
		model.addAttribute("boards", boardService.글목록(pageable));
		model.addAttribute("Total", Total);
		System.out.println(model);
		/*
		 * System.out.println(boardService.글목록(pageable).getNumber()); // 페이지 번호
		 * System.out.println(boardService.글목록(pageable).getTotalElements()); // 총 게시물
		 * 개수
		 */		
		return "index"; // viewResolver 작동
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	@GetMapping("board/{id}")
	public String findById(@PathVariable int id,Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/detail";
	}
	
	@GetMapping("board/{id}/updateForm")
	public String updateForm(@PathVariable int id,Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
}
