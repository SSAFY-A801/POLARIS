package com.ssafy.polaris.following.dto;

import com.ssafy.polaris.regcode.domain.Regcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowResponseDto {
	Long followingId;
	String profileUrl;
	String nickname;
	Regcode regcode;
}
