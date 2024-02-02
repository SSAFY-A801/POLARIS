package com.ssafy.polaris.following.dto;

import com.ssafy.polaris.following.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowListResponseDto {
    List<FollowResponseDto> followings;
}
