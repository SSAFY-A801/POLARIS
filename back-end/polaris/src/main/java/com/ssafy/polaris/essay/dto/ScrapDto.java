package com.ssafy.polaris.essay.dto;

import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.user.domain.User;

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
