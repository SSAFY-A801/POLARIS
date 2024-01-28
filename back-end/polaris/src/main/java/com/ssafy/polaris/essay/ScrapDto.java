package com.ssafy.polaris.essay;

import com.ssafy.polaris.user.User;

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
