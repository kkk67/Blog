package com.cos.blog.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends AbstractValidator<User> {

		private final UserRepository userRepository;

		@Override
		protected void doValidate(User dto, Errors errors) {
				if(userRepository.existsByEmail(dto.getEmail())) {
					errors.rejectValue("email", "이메일 중복 오류", "이미 사용중인 이메일입니다.");
				}
		}
}
