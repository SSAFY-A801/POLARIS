package com.ssafy.polaris.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
	private Long id;
	private String email;
	private String password;
	private Long region;
	private String nickname;
}
