package com.ssafy.polaris.user.service;

import java.util.Map;

import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;

public interface UserService {
	User getUserById(Long userId);

	Object getUserByEmail(String email) throws Exception;

	// TODO: 토큰 2개 반환하는 response dto 필요
	Map<String, String> login(UserLoginRequestDto userLoginRequestDto) throws Exception;

	void join(User user);
}
