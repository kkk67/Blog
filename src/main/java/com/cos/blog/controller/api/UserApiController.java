package com.cos.blog.controller.api; 
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
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
	@PutMapping("/user")
	public ResponseDto<Integer>update(@RequestBody User user){
		userService.회원수정(user);
		// 트랜잭션이 종료되기 때문에 DB 값은 변경이 됐지만
		// 세션값은 변경이 되지 않아서 수정이 안된거처럼 보임
		//세션 등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
}

/*
 * //로그인
 * 
 * @PostMapping("/api/user/login") public ResponseDto<Integer>login(@RequestBody
 * User user,HttpSession session){
 * User principal = userService.로그인(user); // principal(접근 주체)
 * 
 * if(principal != null) { session.setAttribute("principal", principal); }
 * 
 * return new ResponseDto<Integer>(HttpStatus.OK.value(),1); }
 */
