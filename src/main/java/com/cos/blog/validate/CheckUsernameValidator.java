package com.cos.blog.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckUsernameValidator extends AbstractValidator<User> {

		private final UserRepository userRepository;

		@Override
		protected void doValidate(User dto, Errors errors) {
				if(userRepository.existsByUsername(dto.getUsername())) {
					errors.rejectValue("username", "아이디 중복 오류", "이미 사용중인 아이디입니다.");
				}
		}
}
