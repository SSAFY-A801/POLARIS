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
	private Long region;
	private String regionString;
	private String nickname;
	private String profileUrl;


	public UserResponseDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.region = user.getRegcodeId();
		this.regionString =
			user.getRegcode().getSi() + " "
			+ user.getRegcode().getGungu() + " "
			+ user.getRegcode().getDong();
		this.nickname = user.getNickname();
		this.profileUrl = user.getProfileUrl();
	}
}
