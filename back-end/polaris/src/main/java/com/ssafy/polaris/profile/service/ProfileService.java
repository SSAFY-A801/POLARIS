package com.ssafy.polaris.profile.service;
import com.ssafy.polaris.following.dto.FollowListResponseDto;
import com.ssafy.polaris.profile.dto.ProfileRequestDto;
import com.ssafy.polaris.profile.dto.ProfileResponseDto;
import com.ssafy.polaris.profile.response.DefaultResponse;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    public ResponseEntity<DefaultResponse<ProfileResponseDto>> getProfile(Long userId);

    public ResponseEntity<DefaultResponse<String>> updateProfile(Long userId, ProfileRequestDto profileRequest);

    public ResponseEntity<DefaultResponse<String>> followUser(Long userId, Long followUserId);

    ResponseEntity<DefaultResponse<FollowListResponseDto>> getFollowingList(Long userId);
}

