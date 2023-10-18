package com.cos.blog.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateUserDto {
	private int id;
	private String username;
	private String password;
	private String email;
	private String originEmail;
	private Timestamp createDate;
}
