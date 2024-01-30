package com.ssafy.polaris.following;

import com.ssafy.polaris.user.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDto {
	private User follower;
	private User following;
}
