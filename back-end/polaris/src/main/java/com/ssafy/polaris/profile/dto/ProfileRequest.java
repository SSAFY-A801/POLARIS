package com.ssafy.polaris.profile.dto;

import com.ssafy.polaris.regcode.Regcode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileRequest {
    String nickname;
    Regcode regcode;
    String introduction;
}


