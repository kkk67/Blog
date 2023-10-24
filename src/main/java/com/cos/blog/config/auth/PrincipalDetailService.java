package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.Member;
import com.cos.blog.repository.MemberRepository;

@Service
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	// 스프링이 로그인 요청을 가로챌 때 username,password변수 2개를 가로채는데
	//password 부분 처리는 알아서 함
	// username이 DB에 있는지만 확인해주면 됨
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member principal = memberRepository.findByUsername(username)
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. : "+ username);
				});
		System.out.println(principal);
		return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장이 됨.
	}
}
