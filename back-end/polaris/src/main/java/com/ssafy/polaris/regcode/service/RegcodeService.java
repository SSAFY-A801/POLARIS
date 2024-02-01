package com.ssafy.polaris.regcode.service;

import com.ssafy.polaris.regcode.domain.Regcode;
import com.ssafy.polaris.regcode.dto.RegcodeListResponseDto;
import com.ssafy.polaris.regcode.dto.RegcodeResponseDto;

import java.util.List;

public interface RegcodeService {
    RegcodeListResponseDto si();

    RegcodeListResponseDto gungu(String sido);

    RegcodeListResponseDto dong(String sido, String gugun);
}
