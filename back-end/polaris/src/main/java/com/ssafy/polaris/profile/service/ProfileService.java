package com.ssafy.polaris.profile.service;
import com.ssafy.polaris.following.dto.FollowListResponseDto;
import com.ssafy.polaris.following.dto.FollowRequestDto;
import com.ssafy.polaris.profile.dto.ProfileRequestDto;
import com.ssafy.polaris.profile.dto.ProfileResponseDto;

import java.util.List;

public interface ProfileService {
    public ProfileResponseDto getProfile(Long userId);

    public String updateProfile(Long userId, ProfileRequestDto profileRequest);

    public String followUser(Long userId, Long followUserId);

    FollowListResponseDto getFollowingList(Long userId);

    Integer unfollow(Long followerId, List<FollowRequestDto> followRequestDtoList);
}

