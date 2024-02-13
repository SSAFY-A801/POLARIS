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

import com.ssafy.polaris.global.exception.exceptions.WrongPasswordException;
import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.global.security.util.SecurityUtil;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.KakaoProfile;
import com.ssafy.polaris.user.dto.UserJoinRequestDto;
import com.ssafy.polaris.user.dto.UserKakaoJoinRequestDto;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;
import com.ssafy.polaris.user.dto.UserResponseDto;
import com.ssafy.polaris.user.dto.UserSetPasswordDto;
import com.ssafy.polaris.user.exception.CertCodeExpiredException;
import com.ssafy.polaris.user.exception.CertCodeNotMatch;
import com.ssafy.polaris.user.exception.KakaoUserConflictException;
import com.ssafy.polaris.user.exception.UserConflictException;
import com.ssafy.polaris.user.exception.UserKakaoJoined;
import com.ssafy.polaris.user.exception.UserNotFoundException;
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

	/**
	 * 이메일의 사용 여부를 boolean으로 반환한다.
	 * @param email
	 * @return boolean
	 */
	@GetMapping("/email_check/{email}")
	public ResponseEntity<DefaultResponse<Map<String, Boolean>>> emailCheck(@PathVariable("email") String email) {
		boolean result = userService.emailCheck(email);
		StatusCode statusCode = result? StatusCode.EMAIL_IN_USE : StatusCode.EMAIL_NOT_IN_USE;
		return DefaultResponse.toResponseEntity(HttpStatus.OK, statusCode, Map.of("isInUse", result));
	}

	/**
	 * 닉네임의 사용 여부를 boolean으로 반환한다.
	 * @param nickname
	 * @return boolean
	 */
	@GetMapping("/nickname_check/{nickname}")
	public ResponseEntity<DefaultResponse<Map<String, Boolean>>> nicknameCheck(
		@PathVariable("nickname") String nickname) {
		boolean result = userService.nicknameCheck(nickname);
		StatusCode statusCode = result? StatusCode.NICKNAME_IN_USE : StatusCode.NICKNAME_NOT_IN_USE;

		return DefaultResponse.toResponseEntity(HttpStatus.OK, statusCode, Map.of("isInUse", result));
	}

	/**
	 * 일반 회원 가입. 이메일, 닉네임 등의 검사를 포함한다.
	 * @throws UserConflictException 사용중인 이메일, 닉네임이 입력됐을 때
	 * @param userJoinRequestDto
	 * @return userResponseDto
	 */
	@PostMapping
	public ResponseEntity<DefaultResponse<UserResponseDto>> join(@RequestBody UserJoinRequestDto userJoinRequestDto) {
		UserResponseDto userResponseDto = userService.join(userJoinRequestDto);
		return DefaultResponse.toResponseEntity(HttpStatus.CREATED, StatusCode.CREATED_USER, userResponseDto);
	}

	/**
	 * kakao 회원 가입. 이미 가입했는지 여부 등을 검사한다.
	 * @throws KakaoUserConflictException 이미 해당 카카오 계정으로 가입을 했을 때
	 * @throws UserConflictException 사용중인 이메일, 닉네임이 입력됐을 때
	 * @param userKakaoJoinRequestDto
	 * @return userResponseDto
	 */
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

	/**
	 * 일반 회원 로그인.
	 * @throws WrongPasswordException 비밀번호가 틀렸을 때
	 * @throws UserKakaoJoined 카카오회원이었을 때
	 * @param userLoginRequestDto 이메일, 비밀번호가 입력된다.
	 * @return tokenMap 사용자 정보와 토큰을 같이 반환해준다.
	 */
	@PostMapping("/login")
	public ResponseEntity<DefaultResponse<Map<String, String>>> login(
		@RequestBody UserLoginRequestDto userLoginRequestDto) {
		Map<String, String> tokenMap = userService.login(userLoginRequestDto);
		// header에 담아서 주는 것이 더 안전하다.
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_LOGIN, tokenMap);
	}

	/**
	 * kakao 회원 로그인.
	 * 카카오 회원이 아닐 경우는 203코드와 kakaoProfileAndOauthTokens을 반환한다.
	 * @param code
	 * @return tokenMap kakao 회원이 맞을 경우
	 */
	@PostMapping("/login/oauth2/code/kakao")
	public ResponseEntity kakaoLogin(@RequestParam String code) {
		Map<String, Object> kakaoProfileAndOauthTokens = userService.kakaoLoginProcess(code);
		if (userService.isKakaoUser(((KakaoProfile)kakaoProfileAndOauthTokens.get("kakaoProfile")).getId())) {
			// kakao user일 경우 로그인 완료시키기
			User user = userService.getUserByOauth(((KakaoProfile)kakaoProfileAndOauthTokens.get("kakaoProfile")).getId());
			((KakaoProfile)kakaoProfileAndOauthTokens.get("kakaoProfile")).setEmail(user.getEmail());
			Map<String, String> tokenMap = userService.kakaoLogin(
				(KakaoProfile)kakaoProfileAndOauthTokens.get("kakaoProfile"));
			return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_LOGIN, tokenMap);
		}
		else {
			// kakao user 아닐 경우 가입 필요를 알리는 코드 203 발생
			return DefaultResponse.toResponseEntity(HttpStatus.ACCEPTED, StatusCode.KAKAO_JOIN_NEEDED, kakaoProfileAndOauthTokens);
		}
	}

	/**
	 * 액세스 토큰 재발급 로직
	 * @throws UserNotFoundException 해당 이메일을 가진 사람이 없을 때
	 * @param request refresh token을 헤더에서 받기 위해
	 * @param userLoginRequestDto 이메일을 받기 위해
	 * @return tokenMap 새롭게 발급한 access, refresh 토큰
	 */
	@PostMapping("/reissue")
	public ResponseEntity<DefaultResponse<Map<String, String>>> reissue(HttpServletRequest request, @RequestBody UserLoginRequestDto userLoginRequestDto) {
		// TODO : 리프레쉬 토큰 검사 로직
		String refreshToken = SecurityUtil.getAccessToken(request);
		Map<String, String> tokenMap = userService.reissue(refreshToken, userLoginRequestDto.getEmail());
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_REISSUE, tokenMap);
	}

	/**
	 * 로그아웃. 현재 토큰을 레디스에 blacklist로 등록한다.
	 * @param request access token을 헤더에서 받기 위해
	 * @param securityUser 현재 로그인 한 사람 정보
	 * @return null
	 */
	@GetMapping("/logout")
	public ResponseEntity<DefaultResponse<Void>> logout(HttpServletRequest request, @AuthenticationPrincipal SecurityUser securityUser) {
		String accessToken = SecurityUtil.getAccessToken(request);
		userService.logout(accessToken, securityUser);
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_LOGOUT);
	}

	/**
	 * 탈퇴.
	 * @throws UserNotFoundException 가입 회원이 아닐 때
	 * @param securityUser
	 * @return null
	 */
	@DeleteMapping
	public ResponseEntity<DefaultResponse<Void>> resignation(@AuthenticationPrincipal SecurityUser securityUser) {
		userService.resignation(securityUser.getId());
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_RESIGNATION);
	}

	/**
	 * 이메일 확인 코드 일치여부를 확인
	 * @throws CertCodeNotMatch 코드가 매치되지 않을 때
	 * @throws CertCodeExpiredException 코드가 만료되어 찾을 수 없을 때
	 * @param body 이메일을 받기 위해
	 * @return null 코드와 에러로 상태를 알린다.
	 */
	@PostMapping("/email_cert")
	public ResponseEntity<DefaultResponse<Void>> emailCertification(@RequestBody Map<String, String> body) {
		userService.emilCertification(body);
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.EMAIL_CERT_SUCCESS);
	}

	/**
	 * 비밀번호가 일치하는지 검사 후 변경
	 * @throws WrongPasswordException 오래된 패스워드가 실제 패스워드가 아닐 때
	 * @throws UserNotFoundException 가입된 유저를 찾을 수 없을 때
	 * @param passwords 오래된 패스워드, 새 패스워드를 받는다.
	 * @param securityUser
	 * @return null 코드와 에러로 상태를 알린다.
	 */
	@PatchMapping("/change_password")
	public ResponseEntity<DefaultResponse<Void>> changePassword(
		@RequestBody UserSetPasswordDto passwords,
		@AuthenticationPrincipal SecurityUser securityUser) {

		userService.passwordCorrectionCheck(passwords, securityUser);
		userService.setPassword(securityUser.getId(), passwords.getNewPassword());
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_PASSWORD_SET);
	}
}
