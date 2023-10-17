package com.cos.blog.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.service.EmailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MailController {
	private final EmailService emailService;
	
	@PostMapping("/mail")
	public ResponseEntity mail(@RequestBody String mail){
		System.out.println(mail);
		int number = emailService.sendMail(mail);
		
		String num="" + number;
		
		System.out.println("num: "+num);
		return ResponseEntity.ok(num) ;
	}
}
