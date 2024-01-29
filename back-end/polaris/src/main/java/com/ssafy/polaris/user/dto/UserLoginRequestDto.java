package com.ssafy.polaris.user.dto;

import com.ssafy.polaris.chat.domain.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserLoginRequestDto {
	private String email;
	private String password;
}