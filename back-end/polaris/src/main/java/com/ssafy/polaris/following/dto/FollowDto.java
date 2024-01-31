package com.ssafy.polaris.following.dto;

import com.ssafy.polaris.following.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FollowDto {
	private Long followerUserId;
	private List<Follow> followings;
}
