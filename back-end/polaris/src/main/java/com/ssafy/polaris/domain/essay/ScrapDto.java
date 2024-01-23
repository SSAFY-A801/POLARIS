package com.ssafy.polaris.domain.essay;

import com.ssafy.polaris.domain.user.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ScrapDto {
	private int id;
	private Essay essay;
	private User user;
}
