package com.cos.blog.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailService {
	private final JavaMailSender javaMailSender;
	private final String senderEmail ="0154372@gmail.com";
	private static int number;
	
	public static void createNumber() {
		number = (int)(Math.random()*(90000)) + 100000;
	}
	
	public MimeMessage CreateMail(String mail) {
		createNumber();
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, mail);
			message.setSubject("이메일 인증");
			String body="";
			body += "<div style='margin:100px;'>";
			body += "<h1> 안녕하세요</h1>";
			body += "<h1> Blog 입니다</h1>";
			body += "<br/>";
			body += "<div align='center' style='border:1px solid black; font-family:verdana';>";
			body += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
			body += "<div style='font-size:130%'>";
			body += "CODE : <strong>";
			body += number + "</strong><div><br/> "; // 메일에 인증번호 넣기
			body += "</div>";
			message.setText(body, "UTF-8", "html");
		}catch(MessagingException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	public int sendMail(String mail) {
		MimeMessage message = CreateMail(mail);
		javaMailSender.send(message);
		
		return number;
	}
}
