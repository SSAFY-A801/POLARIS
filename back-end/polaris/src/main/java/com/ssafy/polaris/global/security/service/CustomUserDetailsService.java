package com.ssafy.polaris.global.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ssafy.polaris.global.exception.exceptions.UserNotFoundException;
import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		// TODO: 에러 정의
		User user = userRepository.findUserByEmail(username)
			.orElseThrow(() -> new UserNotFoundException("username not found exception has occurred"));
		return new SecurityUser(user);
	}

}