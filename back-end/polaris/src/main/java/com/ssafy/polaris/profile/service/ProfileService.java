package com.ssafy.polaris.profile.service;

import com.ssafy.polaris.book.dto.UserBookResponse;
import com.ssafy.polaris.profile.response.DefaultResponse;
import com.ssafy.polaris.profile.dto.ProfileRequest;
import com.ssafy.polaris.profile.dto.ProfileResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProfileService {
    public ResponseEntity<DefaultResponse<ProfileResponse>> getProfile(Long userId);

    public ResponseEntity<DefaultResponse<String>> updateProfile(Long userId, ProfileRequest profileRequest);

    //public ResponseEntity<DefaultResponse<String>> updatePassword(Long userId, String password);

    public ResponseEntity<DefaultResponse<String>> followUser(Long userId, Long followUserId);

    public ResponseEntity<DefaultResponse<List<UserBookResponse>>> getLibrary(Long userId);
}

