package com.cos.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//서비스는 하나의 트랜잭션(작업 단위)면 상관이 없지만 두 개 이상의 트랜잭션을 한번에 수행하기 위하여 사용한다.
//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private BCryptPasswordEncoder encoder;
		
		@Transactional(readOnly = true)
		public User 회원찾기(String username) {
			User user = userRepository.findByUsername(username).orElseGet(()->{
				return new User();
			});
			
			return user;
		}
		
		@Transactional
		public int 회원가입(User user) {
			try {
				String rawPassword = user.getPassword(); //원문
				String encPassword = encoder.encode(rawPassword); // 해쉬화
				user.setPassword(encPassword);
				user.setRole(RoleType.USER);
				 userRepository.save(user);
				 return 1;
			}catch (Exception e) {
				e.printStackTrace();
				System.out.println("UserService : 회원가입() : " + e.getMessage());
			}
			return -1;
		}
		
		
		
		@Transactional
		public void 회원수정(User user) {
			//수정시에는 영속성 컨텍스트 user 오브젝트를 영속화 시키고 영속화된 user 오브젝트를 수정
			// select를 해서 오브젝트를 가져오는 이유는 영속화를 하기위해서
			User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
				return new IllegalArgumentException("회원 찾기 실패");
			});
			// validation 체크
			if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
				String rawPassword = user.getPassword(); //원문
				String encPassword = encoder.encode(rawPassword); // 해쉬화
				persistance.setPassword(encPassword);
				persistance.setEmail(user.getEmail());				
			}
			
			// 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = 커밋 자동
		}
		
		/*
		 * @Transactional(readOnly = true) // select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성
		 * 유지) public User 로그인(User user) { return
		 * userRepository.findByUsernameAndPassword(user.getUsername(),
		 * user.getPassword());
		 * 
		 * }
		 */
}
