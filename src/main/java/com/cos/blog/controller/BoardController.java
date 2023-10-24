package com.cos.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.model.Board;
import com.cos.blog.model.Member;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.MemberService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	

	//@AuthenticationPrincipal PrincipalDetail principal

	@GetMapping({"/",""})
	public String index(Model model,@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC)Pageable pageable) {
		int Total = boardService.글목록(pageable).getTotalPages();
		model.addAttribute("boards", boardService.글목록(pageable));
		model.addAttribute("isEmpty", boardService.글목록(pageable).isEmpty());
		model.addAttribute("Elements", boardService.글목록(pageable).getTotalElements());
		model.addAttribute("Total", Total);

		System.out.println(model);
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
	@GetMapping("/search")
	public String searchForm(String keyword,Model model,@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC)Pageable pageable) {
		Page<Board> searchList = null;
		
		if(keyword == null) { // 검색어가 없으면 기존의 페이지를 보여줌
			searchList = boardService.글목록(pageable);
		}
		else { // 검색어가 뭐라도 있으면 검색
			searchList = boardService.검색하기(keyword,pageable);
		}
		model.addAttribute("search",searchList); // 검색 결과
		model.addAttribute("keyword", keyword); //검색어
		model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //이전 페이지 번호
		model.addAttribute("next", pageable.next().getPageNumber()); // 다음 페이지 번호
		model.addAttribute("hasNext", searchList.hasNext()); // 다음 페이지가 있는가?
		model.addAttribute("hasPrev", searchList.hasPrevious()); // 이전 페이지가 있는가?
		model.addAttribute("Total", boardService.검색하기(keyword, pageable).getTotalPages()); // 전체 페이지 수
		model.addAttribute("isEmpty", searchList.isEmpty()); //검색 결과가 없는가?
		
		return "board/searchForm";
	}
}
