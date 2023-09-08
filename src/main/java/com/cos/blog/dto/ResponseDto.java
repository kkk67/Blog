package com.cos.blog.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//응답이 잘 되었는지 확인하기 위한 데이터(자바 빈즈)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {
	int status; // 200 / 404 / 500 등
	T data; // 결과 값
}
