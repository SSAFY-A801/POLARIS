package com.ssafy.polaris.user.service;

import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.dto.UserLoginRequestDto;

public interface UserService {

	User getUserById(Long userId);
}
