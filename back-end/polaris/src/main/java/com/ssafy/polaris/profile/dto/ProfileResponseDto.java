package com.ssafy.polaris.profile.dto;

import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.regcode.domain.Regcode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProfileResponseDto {
    Regcode regcode;
    String nickname;
    String profileUrl;
    String introduction;
    int followingsCnt;
    int tradingCnt;
    int exchangeCnt;
}
