package com.ssafy.polaris.domain.following;

import com.ssafy.polaris.domain.user.User;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowDto {
	private User follower;
	private User following;
}
