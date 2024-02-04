package com.ssafy.polaris.following.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowListRequestDto {
    List<FollowRequestDto> unfollowings;
}
