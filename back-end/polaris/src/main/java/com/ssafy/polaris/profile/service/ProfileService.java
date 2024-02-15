package com.ssafy.polaris.profile.service;

import java.io.IOException;
import java.util.List;

import com.ssafy.polaris.following.dto.FollowListResponseDto;
import com.ssafy.polaris.following.dto.FollowRequestDto;
import com.ssafy.polaris.profile.dto.ProfileRequestDto;
import com.ssafy.polaris.profile.dto.ProfileResponseDto;

public interface ProfileService {
	public ProfileResponseDto getProfile(Long userId);

	public String updateProfile(Long userId, ProfileRequestDto profileRequest) throws IOException;

	public String followUser(Long userId, Long followUserId);

	FollowListResponseDto getFollowingList(Long userId);

	Integer unfollow(Long followerId, List<FollowRequestDto> followRequestDtoList);
}

