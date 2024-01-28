package com.ssafy.polaris.user.service;

import com.ssafy.polaris.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserService {
	User getUserById(Long userId);
}
