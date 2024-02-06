package com.ssafy.polaris.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.global.security.util.SecurityUtil;
import com.ssafy.polaris.user.dto.UserJoinRequestDto;
import com.ssafy.polaris.user.response.DefaultResponse;
import com.ssafy.polaris.user.response.StatusCode;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.UserResponseDto;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;
import com.ssafy.polaris.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/email_check/{email}")
	public ResponseEntity<DefaultResponse<Map<String, Boolean>>> emailCheck(@PathVariable("email") String email) {
		boolean result = userService.emailCheck(email);
		StatusCode statusCode = StatusCode.EMAIL_NOT_IN_USE;
		if (result)
			statusCode = StatusCode.EMAIL_IN_USE;

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			statusCode,
			Map.of("isInUse", result)
		);
	}

	@GetMapping("/nickname_check/{nickname}")
	public ResponseEntity<DefaultResponse<Map<String, Boolean>>> nicknameCheck(@PathVariable("nickname") String nickname) {
		boolean result = userService.nicknameCheck(nickname);
		StatusCode statusCode = StatusCode.NICKNAME_NOT_IN_USE;
		if (result)
			statusCode = StatusCode.NICKNAME_IN_USE;

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			statusCode,
			Map.of("isInUse", result)
		);
	}

	// TODO : 컨트롤러에 작업이 너무 많다. 서비스 쪽으로 옮길수 있도록 리팩토링
	@PostMapping
	public ResponseEntity<DefaultResponse<UserResponseDto>> join(@RequestBody UserJoinRequestDto userJoinRequestDto) {
		// TODO: findUserByEmail, Nickname등을 사용하여 중복된다면 거부
		boolean isEmailInUse = userService.emailCheck(userJoinRequestDto.getEmail());
		boolean isNicknameInUse = userService.nicknameCheck(userJoinRequestDto.getNickname());

		if (isEmailInUse || isNicknameInUse) {
			return DefaultResponse.emptyResponse(
				HttpStatus.CONFLICT,
				StatusCode.USER_EMAIL_OR_NICKNAME_CONFLICT
			);
		}

		String encodedPassword = passwordEncoder.encode(userJoinRequestDto.getPassword());
		userJoinRequestDto.setPassword("");
		User user =  User.builder()
			.email(userJoinRequestDto.getEmail())
			.password(encodedPassword)
			.nickname(userJoinRequestDto.getNickname())
			.regcodeId(userJoinRequestDto.getRegion()).build();
		userService.join(user);

		return DefaultResponse.toResponseEntity(
			HttpStatus.CREATED,
			StatusCode.CREATED_USER,
			UserResponseDto.builder()
				.id(user.getId())
				.email(user.getEmail())
				.region(user.getRegcodeId())
				.nickname(user.getNickname())
				.build()
		);
	}

	@PostMapping("/login")
	public ResponseEntity<DefaultResponse<Map<String, String>>> login(@RequestBody UserLoginRequestDto userLoginRequestDto) throws Exception {
		Map<String, String> tokenMap = userService.login(userLoginRequestDto);
		// TODO: "어떤 토큰"을 "어디에 담아서" 반환할 것인지 정해야 한다.
		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_LOGIN,
			tokenMap
		);
	}

	@PostMapping("/logout")
	public ResponseEntity<DefaultResponse<Void>> logout(HttpServletRequest request) {
		String accessToken = SecurityUtil.getAccessToken(request);
		if (accessToken == null) {
			return DefaultResponse.emptyResponse(
				HttpStatus.UNAUTHORIZED,
				StatusCode.NO_ACCESS_TOKEN
			);
		}
		// TODO : 검증이 되면 Redis에 저장되어 있던 Email(key)과 Refresh Token(value)을 삭제한다.
		// TODO : Access Token을 key “logout” 문자열을 value로 Redis에 저장하여 해당 토큰을 Black List 처리한다.
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_LOGOUT
		);
	}

	// TODO: 삭제한 회원의 권한 관리 등 처리해주기
	@DeleteMapping
	public ResponseEntity<DefaultResponse<Void>> resignation(@AuthenticationPrincipal SecurityUser securityUser) throws Exception {
		userService.resignation(securityUser.getId());
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_RESIGNATION
		);
	}

	@PostMapping("/email_cert")
	public ResponseEntity<DefaultResponse<Void>> emailCertification(@RequestBody Map<String, String> body) {
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.EMAIL_NOT_IN_USE
		);

	}
}
