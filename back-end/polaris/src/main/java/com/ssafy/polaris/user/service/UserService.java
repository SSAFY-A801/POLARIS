package com.ssafy.polaris.user.service;

import java.util.Map;

import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.KakaoProfile;
import com.ssafy.polaris.user.dto.UserJoinRequestDto;
import com.ssafy.polaris.user.dto.UserKakaoJoinRequestDto;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;
import com.ssafy.polaris.user.dto.UserResponseDto;
import com.ssafy.polaris.user.dto.UserSetPasswordDto;

import jakarta.servlet.http.HttpSession;

public interface UserService {
	User getUserById(Long userId);

	User getUserByEmail(String email);

	User getUserByNickname(String nickname);

	UserResponseDto kakaoJoin(UserKakaoJoinRequestDto userKakaoJoinRequestDto);

	Map<String, String> login(UserLoginRequestDto userLoginRequestDto);

	UserResponseDto join(UserJoinRequestDto userJoinRequestDto);

	Boolean emailCheck(String email);

	Boolean nicknameCheck(String nickname);

	void resignation(Long id);

	void setPassword(Long id, String password);

	void passwordCorrectionCheck(UserSetPasswordDto passwords, SecurityUser securityUser);

	void logout(String accessToken, SecurityUser securityUser);

	Map<String, String> reissue(String refreshToken, String email);

	void emilCertification(Map<String, String> body);

	Map<String, String> kakaoLogin(KakaoProfile kakaoProfile);

	Map<String, Object> kakaoLoginProcess(String code);

	boolean isKakaoUser(Long kakaoProfileId);

	User getUserByOauth(Long oauth);
}
