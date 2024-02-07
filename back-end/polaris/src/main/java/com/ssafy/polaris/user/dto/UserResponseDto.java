package com.ssafy.polaris.user.dto;

import com.ssafy.polaris.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserResponseDto {
	private Long id;
	private String email;
	// TODO : 패스워드 반환할 일은 없을 듯? 테스트 후 삭제
	// private String password;
	private Long region;
	private String regionString;
	private String nickname;

	// public UserResponseDto() {
	// }

	public UserResponseDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.region = user.getRegcodeId();
		this.regionString =
			user.getRegcode().getSi() + " "
			+ user.getRegcode().getGungu() + " "
			+ user.getRegcode().getDong();
		this.nickname = user.getNickname();
	}

	// public UserResponseDto(SecurityUser securityUser) {
	// 	this.id = securityUser.getId();
	// 	this.email = securityUser.getEmail();
	// 	this.nickname = securityUser.getNickname();
	// }
}
