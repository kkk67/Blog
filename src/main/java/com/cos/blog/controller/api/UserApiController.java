package com.cos.blog.controller.api; 
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	//private BCryptPasswordEncoder encode;
	//	System.out.println("UserAptiController: save 호출됨");
	// 실제로 DB에 insert하고 아래에서 return만 되면 됨.

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer>save(@RequestBody User user) {
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/loginForm";
	}
}


/*
 * //로그인
 * 
 * @PostMapping("/api/user/login") public ResponseDto<Integer>login(@RequestBody
 * User user,HttpSession session){
 * System.out.println("UserAptiController: login 호출됨"); User principal =
 * userService.로그인(user); // principal(접근 주체)
 * 
 * if(principal != null) { session.setAttribute("principal", principal); }
 * 
 * return new ResponseDto<Integer>(HttpStatus.OK.value(),1); }
 */
