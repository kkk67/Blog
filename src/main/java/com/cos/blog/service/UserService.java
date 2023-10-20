package com.cos.blog.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.UpdateUserDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

//서비스는 하나의 트랜잭션(작업 단위)면 상관이 없지만 두 개 이상의 트랜잭션을 한번에 수행하기 위하여 사용한다.
//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
@Service
public class UserService {

		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private BoardRepository boardRepository;
		
		@Autowired
		private ReplyRepository replyRepository;
		
		@Autowired
		private BCryptPasswordEncoder encoder;
		
		
		@Transactional(readOnly = true)
		public User 회원이름찾기(String username) {
			User user = userRepository.findByUsername(username).orElseGet(()->{
				return new User();
			});
			
			return user;
		}
		
		@Transactional(readOnly = true)
		public User 회원아이디찾기(int id) {
			System.out.println("받은 id 값: " + id);
			return userRepository.findById(id).orElseThrow(()->{
				return new  IllegalArgumentException("회원 찾기 실패");
			});
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
			if((persistance.getOauth() == null || persistance.getOauth().equals(""))) {
				if(user.getPassword() == null) {
					persistance.setEmail(user.getEmail());	
				}
				else {
					String rawPassword = user.getPassword(); //원문
					String encPassword = encoder.encode(rawPassword); // 해쉬화
					persistance.setPassword(encPassword);					
				}
			}
			
			// 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = 커밋 자동
		}
		
		@Transactional
		public void 회원탈퇴(int id) {	
			Optional<User> userEntity = userRepository.findById(id);
			
			if(boardRepository.existsByUser(userEntity.get())) { // 회원이 작성한 게시글이 있으면
				List<Board> boardEntity = boardRepository.findByUser(userEntity.get()); 
				for(int i=0; i<boardEntity.size(); i++) { // 게시글 만큼
					if(replyRepository.existsByBoardId(boardEntity.get(i).getId())) { // 게시글 안에 댓글이 있으면(본인과 다른사람의 댓글)
						replyRepository.deleteByBoardId(boardEntity.get(i).getId()); // 댓글 삭제
					}
					boardRepository.deleteById(boardEntity.get(i).getId()); // 게시글 삭제
				}
			}
			if(replyRepository.existsByUser(userEntity.get())) { // 회원이 작성한 댓글이 있다면
				List<Reply> ReplyList = replyRepository.findByUser(userEntity.get()); 
				
				for(int i=0; i<ReplyList.size(); i++) { 
					replyRepository.deleteById(ReplyList.get(i).getId()); // 댓글 삭제
				}
			}
			
			 userRepository.deleteById(id);  //회원탈퇴
		}
		
		// true: 중복
		@Transactional(readOnly = true)
		public String checkUsernameDuplication(String username) {
			boolean usernameDuplicate = userRepository.existsByUsername(username);

			if(usernameDuplicate == true) { // 중복
				return "아이디가 중복됩니다.";
			}else {
				return "사용가능한 아이디입니다.";
			}
			
		}
		
		@Transactional(readOnly = true)
		public String checkEmailDuplication(String email) {
			boolean emailDuplicate = userRepository.existsByEmail(email);
			
			if(emailDuplicate == true) { // 중복
				return "이메일이 중복됩니다.";
			}else {
				return "사용가능한 이메일입니다..";
			}
		}
		
		@Transactional(readOnly = true)
		public Page<User>회원목록(Pageable pageable){
			return userRepository.findAll(pageable);
		}
		
		/*
		 * @Transactional public void UsernameError(String username) { boolean
		 * usernameDuplicate = userRepository.existsByUsername(username);
		 * if(usernameDuplicate) { throw new IllegalStateException("이미 존재하는 아이디입니다."); }
		 * }
		 * 
		 * @Transactional public void EmailError(String email) { boolean emailDuplicate
		 * = userRepository.existsByEmail(email); if(emailDuplicate) { throw new
		 * IllegalStateException("이미 존재하는 이메일입니다."); } }
		 */
}
