package com.ssafy.polaris.profile.dto;

import com.ssafy.polaris.regcode.domain.Regcode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileRequestDto {
    String nickname;
    Regcode regcode;
    String introduction;
    String imageUrl;
}


