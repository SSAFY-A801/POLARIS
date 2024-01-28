package com.ssafy.polaris.profile.dto;

import com.ssafy.polaris.following.Follow;
import com.ssafy.polaris.regcode.Regcode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    Regcode regcode;
    String nickname;
    String profileUrl;
    String introduction;
    List<Follow> followings = new ArrayList<>();
}
