package com.ssafy.polaris.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.global.security.util.SecurityUtil;
import com.ssafy.polaris.user.dto.KakaoProfile;
import com.ssafy.polaris.user.dto.UserJoinRequestDto;
import com.ssafy.polaris.user.dto.UserKakaoJoinRequestDto;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;
import com.ssafy.polaris.user.dto.UserResponseDto;
import com.ssafy.polaris.user.dto.UserSetPasswordDto;
import com.ssafy.polaris.user.exception.KakaoUserConflictException;
import com.ssafy.polaris.user.exception.UserConflictException;
import com.ssafy.polaris.user.response.DefaultResponse;
import com.ssafy.polaris.user.response.StatusCode;
import com.ssafy.polaris.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping("/email_check/{email}")
	public ResponseEntity<DefaultResponse<Map<String, Boolean>>> emailCheck(@PathVariable("email") String email) {
		boolean result = userService.emailCheck(email);
		StatusCode statusCode = result? StatusCode.EMAIL_IN_USE : StatusCode.EMAIL_NOT_IN_USE;
		return DefaultResponse.toResponseEntity(HttpStatus.OK, statusCode, Map.of("isInUse", result));
	}

	@GetMapping("/nickname_check/{nickname}")
	public ResponseEntity<DefaultResponse<Map<String, Boolean>>> nicknameCheck(
		@PathVariable("nickname") String nickname) {
		boolean result = userService.nicknameCheck(nickname);
		StatusCode statusCode = result? StatusCode.NICKNAME_IN_USE : StatusCode.NICKNAME_NOT_IN_USE;

		return DefaultResponse.toResponseEntity(HttpStatus.OK, statusCode, Map.of("isInUse", result));
	}

	@PostMapping
	public ResponseEntity<DefaultResponse<UserResponseDto>> join(@RequestBody UserJoinRequestDto userJoinRequestDto) {
		UserResponseDto userResponseDto = userService.join(userJoinRequestDto);
		return DefaultResponse.toResponseEntity(HttpStatus.CREATED, StatusCode.CREATED_USER, userResponseDto);
	}

	@PostMapping("/join/oauth2/code/kakao")
	public ResponseEntity<DefaultResponse<UserResponseDto>> kakaoJoin(@RequestBody UserKakaoJoinRequestDto userKakaoJoinRequestDto) {
		if (userService.isKakaoUser(userKakaoJoinRequestDto.getKakaoId())) {
			throw new KakaoUserConflictException("");
		}
		else {
			UserResponseDto userResponseDto = userService.kakaoJoin(userKakaoJoinRequestDto);
			return DefaultResponse.toResponseEntity(HttpStatus.CREATED, StatusCode.CREATED_USER, userResponseDto);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<DefaultResponse<Map<String, String>>> login(
		@RequestBody UserLoginRequestDto userLoginRequestDto) {
		Map<String, String> tokenMap = userService.login(userLoginRequestDto);
		// header에 담아서 주는 것이 더 안전하다.
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_LOGIN, tokenMap);
	}

	@PostMapping("/login/oauth2/code/kakao")
	public ResponseEntity kakaoLogin(@RequestParam String code) {
		Map<String, Object> kakaoProfileAndOauthTokens = userService.kakaoLoginProcess(code);
		if (userService.isKakaoUser(((KakaoProfile)kakaoProfileAndOauthTokens.get("kakaoProfile")).getId())) {
			Map<String, String> tokenMap = userService.kakaoLogin(
				(KakaoProfile)kakaoProfileAndOauthTokens.get("kakaoProfile"));
			return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_LOGIN, tokenMap);
		}
		else {
			return DefaultResponse.toResponseEntity(HttpStatus.ACCEPTED, StatusCode.KAKAO_JOIN_NEEDED, kakaoProfileAndOauthTokens);
		}
	}

	@PostMapping("/reissue")
	public ResponseEntity<DefaultResponse<Map<String, String>>> reissue(HttpServletRequest request, @RequestBody UserLoginRequestDto userLoginRequestDto) {
		String refreshToken = SecurityUtil.getAccessToken(request);
		Map<String, String> tokenMap = userService.reissue(refreshToken, userLoginRequestDto.getEmail());
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_REISSUE, tokenMap);
	}

	@GetMapping("/logout")
	public ResponseEntity<DefaultResponse<Void>> logout(HttpServletRequest request, @AuthenticationPrincipal SecurityUser securityUser) {
		String accessToken = SecurityUtil.getAccessToken(request);
		userService.logout(accessToken, securityUser);
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_LOGOUT);
	}

	@DeleteMapping
	public ResponseEntity<DefaultResponse<Void>> resignation(@AuthenticationPrincipal SecurityUser securityUser) {
		userService.resignation(securityUser.getId());
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_RESIGNATION);
	}

	@PostMapping("/email_cert")
	public ResponseEntity<DefaultResponse<Void>> emailCertification(@RequestBody Map<String, String> body) {
		userService.emilCertification(body);
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.EMAIL_CERT_SUCCESS);
	}

	@PatchMapping("/change_password")
	public ResponseEntity<DefaultResponse<Void>> passwordCorrectionCheck(
		@RequestBody UserSetPasswordDto passwords,
		@AuthenticationPrincipal SecurityUser securityUser) {

		userService.passwordCorrectionCheck(passwords, securityUser);
		userService.setPassword(securityUser.getId(), passwords.getNewPassword());
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_PASSWORD_SET);
	}
}
