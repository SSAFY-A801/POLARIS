package com.ssafy.polaris.essay.service;

import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;

public interface EssayService {
	EssayResponseDto writeEssay(EssayRequestDto essay);

	EssayResponseDto getEssay(Long essayId);
}
