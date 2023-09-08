package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		
		/*
		 * @Transactional(readOnly = true) // select할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성
		 * 유지) public User 로그인(User user) { return
		 * userRepository.findByUsernameAndPassword(user.getUsername(),
		 * user.getPassword());
		 * 
		 * }
		 */
}
