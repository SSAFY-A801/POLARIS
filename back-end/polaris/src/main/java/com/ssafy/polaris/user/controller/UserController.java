package com.ssafy.polaris.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.user.response.DefaultResponse;
import com.ssafy.polaris.user.response.StatusCode;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.UserResponseDto;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;
import com.ssafy.polaris.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping
	public ResponseEntity<DefaultResponse<UserResponseDto>> join(@RequestBody UserResponseDto userResponseDto) {
		// TODO: findUserByEmail, Nickname등을 사용하여 중복된다면 거부
		String encodedPassword = passwordEncoder.encode(userResponseDto.getPassword());
		userResponseDto.setPassword("");
		User user =  User.builder()
			.email(userResponseDto.getEmail())
			.password(encodedPassword)
			.nickname(userResponseDto.getNickname())
			.regcodeId(userResponseDto.getRegion()).build();
		userService.join(user);
		userResponseDto.setId(user.getId());

		return DefaultResponse.toResponseEntity(
			HttpStatus.CREATED,
			StatusCode.CREATED_USER,
			userResponseDto
		);
	}

	@PostMapping("/login")
	public ResponseEntity<DefaultResponse<Map>> login(@RequestBody UserLoginRequestDto userLoginRequestDto) throws Exception {
		Map<String, String> tokenMap = userService.login(userLoginRequestDto);
		// TODO: "어떤 토큰"을 "어디에 담아서" 반환할 것인지 정해야 한다.
		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_LOGIN,
			Map.of("access", tokenMap.get("access"))
		);
	}
}
