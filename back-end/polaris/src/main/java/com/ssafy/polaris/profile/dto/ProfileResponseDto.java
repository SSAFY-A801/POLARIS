package com.ssafy.polaris.profile.dto;

import com.ssafy.polaris.regcode.domain.Regcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileResponseDto {
	Long id;
	Regcode regcode;
	String nickname;
	String profileUrl;
	String introduction;
	int followingsCnt;
	int tradingCnt;
	int exchangeCnt;
}
