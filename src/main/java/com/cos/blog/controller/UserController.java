package com.cos.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/** /css/** /image/**

@Controller
public class UserController {
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	@GetMapping("/auth/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	@GetMapping("/auth/kakao/callback")
	public  String kakaoCallback(String code) { // 데이터를 리턴해주는 컨트롤러 함수
		
		//POST방식으로 key-value 데이터를 요청(카카오쪽으로)
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpBody 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "cdee08dfe352b3104e97fedffd2f90d6");
		params.add("redirect_url", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenReqeust = 
				new HttpEntity<>(params,headers);
		
		//Http 요청하기 - post방식으로 - 그리고 response변수의 응답
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenReqeust,
				String.class
			);
		
		// Gson , Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			 oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("카카오 엑세스 토큰: "+oauthToken.getAccess_token());
		
		
		//POST방식으로 key-value 데이터를 요청(카카오쪽으로)
				RestTemplate rt2 = new RestTemplate();
				
				// HttpHeader 생성
				HttpHeaders headers2 = new HttpHeaders();
				headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
				headers2.add("content-type", "application/x-www-form-urlencoded;charset=utf-8");
				
				// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
				HttpEntity<MultiValueMap<String, String>> kakaoProfileReqeust = 
						new HttpEntity<>(headers2);
				
				//Http 요청하기 - post방식으로 - 그리고 response변수의 응답
				ResponseEntity<String> response2 = rt2.exchange(
						"https://kapi.kakao.com/v2/user/me",
						HttpMethod.POST,
						kakaoProfileReqeust,
						String.class
					);
				
				ObjectMapper objectMapper2 = new ObjectMapper();
				KakaoProfile kakaoProfile = null;
				try {
					kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
		
				//User 오브젝트 : username,password,email
				System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
				System.out.println("카카오 이메일 : "+ kakaoProfile.getKakao_account().getEmail());

				System.out.println("블로그서버 유저네임: " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
				System.out.println("블로그서버 이메일: " + kakaoProfile.getKakao_account().getEmail());
				System.out.println("블로그서버 비밀번호: " + cosKey);
				
				User kakaouser = User.builder()
						.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
						.password(cosKey)
						.email(kakaoProfile.getKakao_account().getEmail())
						.oauth("kakao")
						.build();
				
				//가입자 혹은 비가입자 체크 해서 처리
				User originUser = userService.회원찾기(kakaouser.getUsername());
				
				if(originUser.getUsername() == null) {
					userService.회원가입(kakaouser);					
				}
				// 로그인 처리
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaouser.getUsername(), cosKey));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
		return "redirect:/";
	}
}
