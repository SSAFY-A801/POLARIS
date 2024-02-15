package com.ssafy.polaris.email.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.email.domain.EmailMessage;
import com.ssafy.polaris.email.dto.EmailPostDto;
import com.ssafy.polaris.email.dto.EmailResponseDto;
import com.ssafy.polaris.email.response.DefaultResponse;
import com.ssafy.polaris.email.response.StatusCode;
import com.ssafy.polaris.email.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/send_mail")
@RestController
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailServiceImpl;

	// 임시 비밀번호 발급
	@PostMapping("/password")
	public ResponseEntity<DefaultResponse<Void>> sendPasswordMail(@RequestBody EmailPostDto emailPostDto) {
		EmailMessage emailMessage = EmailMessage.builder()
			.to(emailPostDto.getEmail())
			.subject("[북극성] 임시 비밀번호 발급")
			.build();

		emailServiceImpl.sendMail(emailMessage, "password");

		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.PASSWORD_EMAIL_SUCCEEDED);
	}

	// 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
	@PostMapping("/email")
	public ResponseEntity<DefaultResponse<Void>> sendJoinMail(@RequestBody EmailPostDto emailPostDto) {
		log.info("email : " + emailPostDto.getEmail());
		EmailMessage emailMessage = EmailMessage.builder()
			.to(emailPostDto.getEmail())
			.subject("[북극성] 이메일 인증을 위한 인증 코드 발송")
			.build();
		log.info("emailMessage.email : " + emailMessage.getTo());

		emailServiceImpl.sendMail(emailMessage, "email");

		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.CERTIFICATION_EMAIL_SEND_SUCCEEDED);
	}
}