package com.ssafy.polaris.user.service;

import java.util.Map;

import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.UserJoinRequestDto;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;
import com.ssafy.polaris.user.dto.UserResponseDto;
import com.ssafy.polaris.user.dto.UserSetPasswordDto;

public interface UserService {
	User getUserById(Long userId);

	User getUserByEmail(String email) throws Exception;

	User getUserByNickname(String nickname) throws Exception;

	Map<String, String> login(UserLoginRequestDto userLoginRequestDto) throws Exception;

	UserResponseDto join(UserJoinRequestDto userJoinRequestDto);

	Boolean emailCheck(String email);

	Boolean nicknameCheck(String nickname);

	void resignation(Long id);

	void setPassword(Long id, String password);

	void passwordCorrectionCheck(UserSetPasswordDto passwords, SecurityUser securityUser);

	void logout(String accessToken, SecurityUser securityUser);

	Map<String, String> reissue(String refreshToken, String email);
}
