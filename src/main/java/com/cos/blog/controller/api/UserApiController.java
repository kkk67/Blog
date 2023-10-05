package com.cos.blog.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@Valid @RequestBody User user, BindingResult bindingResult) {
		/*
		 * if(bindingResult.hasErrors()) { return "/auth/joinForm"; }
		 */

		
		  userService.UsernameError(user.getUsername());
		  userService.EmailError(user.getEmail());
		 

		try {
			userService.회원가입(user);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("joinFailed", "이미 등록된 사용자입니다.");
		} catch (IllegalStateException e) {
			e.printStackTrace();
			bindingResult.reject("joinFailed", "이미 등록된 아이디 혹은 이메일 입니다.");
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("joinFailed", e.getMessage());
		}

		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}

	@GetMapping("/auth/joinProc/{username}/existsUsername")
	public String checkId(@PathVariable String username) {

		String result = "N";
		boolean flag = userService.checkUsernameDuplication(username);

		System.out.println(flag);
		if (flag == true)
			result = "Y";

		return result;
	}

	@GetMapping("/auth/joinProc/{email}/existsEmail")
	public String checkEmail(@PathVariable String email) {

		String result = "N";
		boolean flag = userService.checkEmailDuplication(email);
		System.out.println(flag);

		if (flag == true)
			result = "Y";

		return result;
	}

	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {
		userService.회원수정(user);
		// 트랜잭션이 종료되기 때문에 DB 값은 변경이 됐지만
		// 세션값은 변경이 되지 않아서 수정이 안된거처럼 보임
		// 세션 등록
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@DeleteMapping("/user/{id}")
	public ResponseDto<Integer> delete(@PathVariable int id) {
		userService.회원탈퇴(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@GetMapping("/user/{username}")
	public ResponseDto<Integer> checkUsername(@PathVariable String username) {
		User user = userService.회원찾기(username);
		System.out.println(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

}

/*
 * //로그인
 * 
 * @PostMapping("/api/user/login") public ResponseDto<Integer>login(@RequestBody
 * User user,HttpSession session){ User principal = userService.로그인(user); //
 * principal(접근 주체)
 * 
 * if(principal != null) { session.setAttribute("principal", principal); }
 * 
 * return new ResponseDto<Integer>(HttpStatus.OK.value(),1); }
 */
