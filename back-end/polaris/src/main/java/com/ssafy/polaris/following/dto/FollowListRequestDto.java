package com.ssafy.polaris.following.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowListRequestDto {
	List<FollowRequestDto> unfollowings;
}
