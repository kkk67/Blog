package com.cos.blog.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.dto.ValidPWDto;
import com.cos.blog.dto.deleteUserDto;
import com.cos.blog.model.User;
import com.cos.blog.service.EmailService;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private EmailService emailService;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@Valid @RequestBody User user, BindingResult bindingResult) {
		/*
		 * if(bindingResult.hasErrors()) { return "/auth/joinForm"; }
		 */

		
		 String resultUsername= userService.checkUsernameDuplication(user.getUsername());
		  String resultEmail = userService.checkEmailDuplication(user.getEmail());
		  
		  String disableUsername="아이디가 중복됩니다.";
		  String disableEmail="이메일이 중복됩니다.";
		  
		if(resultUsername.equals(disableUsername) && resultEmail.equals(disableEmail)) { // 둘다 중복
			throw new IllegalStateException("아이디와 이메일이 모두 중복됩니다.");
		}
		else if(resultUsername.equals(disableUsername)) {
			throw new IllegalStateException("중복되는 아이디입니다.");
		}
		else if(resultEmail.equals(disableEmail)) {
			throw new IllegalStateException("중복되는 이메일입니다..");
		}
		 
		userService.회원가입(user);


		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}

	@GetMapping("/auth/joinProc/{username}/existsUsername")
	public String checkId(@PathVariable String username) {

		String result = "N";
		String flag = userService.checkUsernameDuplication(username);

		System.out.println(flag);
		if (flag.equals("아이디가 중복됩니다."))
			result = "Y";

		return result;
	}

	@GetMapping("/auth/joinProc/{email}/existsEmail")
	public String checkEmail(@PathVariable String email) {

		String result = "N";
		String flag = userService.checkEmailDuplication(email);
		System.out.println(flag);

		if (flag.equals("이메일이 중복됩니다."))
			result = "Y";

		return result;
	}

	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) { //회원정보 수정
				userService.회원수정(user);
			
		// 트랜잭션이 종료되기 때문에 DB 값은 변경이 됐지만
		// 세션값은 변경이 되지 않아서 수정이 안된거처럼 보임
		// 세션 등록
		
		/*
		 * Authentication authentication = authenticationManager .authenticate(new
		 * UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		 * SecurityContextHolder.getContext().setAuthentication(authentication);
		 */
		 
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@DeleteMapping("/user/{id}")
	public ResponseDto<Integer> delete(@RequestBody deleteUserDto deleteUserDto) {
		User user = userService.회원아이디찾기(deleteUserDto.getUserid());
		if(deleteUserDto.getPassword().equals("")) {
			throw new BadCredentialsException("비밀번호를 입력해주세요");
		}
		
		 boolean ismatch = encoder.matches(deleteUserDto.getPassword(), user.getPassword());
		 
		 if(ismatch == true) {
				userService.회원탈퇴(deleteUserDto.getUserid());			 
		 }
		 else {
			 throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
		 }
		 
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@GetMapping("/user/{username}")
	public ResponseDto<Integer> checkUsername(@PathVariable String username) {
		User user = userService.회원이름찾기(username);
		System.out.println(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/user/validPwd")
	public  @ResponseBody String validPwd(@RequestBody ValidPWDto validPWDto){
		System.out.println(validPWDto);
		
		  User user = userService.회원아이디찾기(validPWDto.getUserid());
		  System.out.println(user);
		  
		  boolean ismatch = encoder.matches(validPWDto.getPassword(), user.getPassword()); // 평문,암호문 순으로 넣어야 됨
		 		
		  String result = null;
		
		
			if (validPWDto.getPassword() == null) {
				result = null;
			}
			else if(ismatch == true) {
				result = "Y";
			}
			else {
				result="N";
			}
		 
		System.out.println(result);
		return result;
	}
}
