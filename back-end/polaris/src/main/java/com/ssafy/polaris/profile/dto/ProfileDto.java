package com.ssafy.polaris.profile.dto;

import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.regcode.domain.Regcode;
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

    public ProfileDto(Object[] tuple) {
        this.regcode = (Regcode) tuple[0];
        this.nickname = (String) tuple[1];
        this.profileUrl = (String) tuple[2];
        this.introduction = (String) tuple[3];
    }
}
