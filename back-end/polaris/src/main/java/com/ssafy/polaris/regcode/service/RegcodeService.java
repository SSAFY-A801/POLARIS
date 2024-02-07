package com.ssafy.polaris.regcode.service;

import com.ssafy.polaris.regcode.dto.RegcodeListResponseDto;

public interface RegcodeService {
	RegcodeListResponseDto si();

	RegcodeListResponseDto gungu(String sido);

	RegcodeListResponseDto dong(String sido, String gugun);
}
