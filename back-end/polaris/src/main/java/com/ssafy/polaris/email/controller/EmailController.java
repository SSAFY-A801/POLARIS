package com.ssafy.polaris.email.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.email.dto.EmailResponseDto;
import com.ssafy.polaris.email.domain.EmailMessage;
import com.ssafy.polaris.email.dto.EmailPostDto;
import com.ssafy.polaris.email.response.DefaultResponse;
import com.ssafy.polaris.email.response.StatusCode;
import com.ssafy.polaris.email.service.EmailService;

@CrossOrigin
@RequestMapping("/send-mail")
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

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.PASSWORD_EMAIL_SUCCEEDED,
			null
		);
	}

	// 회원가입 이메일 인증 - 요청 시 body로 인증번호 반환하도록 작성하였음
	@PostMapping("/email")
	public ResponseEntity sendJoinMail(@RequestBody EmailPostDto emailPostDto) {
		EmailMessage emailMessage = EmailMessage.builder()
			.to(emailPostDto.getEmail())
			.subject("[북극성] 이메일 인증을 위한 인증 코드 발송")
			.build();

		String code = emailServiceImpl.sendMail(emailMessage, "email");

		EmailResponseDto emailResponseDto = new EmailResponseDto();
		emailResponseDto.setCode(code);

		// TODO : redis 등에 저장하여 시간 내로 검사하는 로직 추가 필요

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.CERTIFICATION_EMAIL_SEND_SUCCEEDED,
			null
		);
	}
}