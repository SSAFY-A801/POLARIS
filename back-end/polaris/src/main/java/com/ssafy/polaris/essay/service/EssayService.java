package com.ssafy.polaris.essay.service;

import java.util.List;

import com.ssafy.polaris.common.SearchConditions;
import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;
import com.ssafy.polaris.security.SecurityUser;

public interface EssayService {
	EssayResponseDto writeEssay(EssayRequestDto essay, SecurityUser securityUser);

	EssayResponseDto getEssay(Long essayId);
	EssayResponseDto updateEssay(EssayRequestDto essayRequestDto);

	void deleteEssay(Long essayId);

	List<EssayResponseDto> getEssayList(SearchConditions searchConditions);
}
