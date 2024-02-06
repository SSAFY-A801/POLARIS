package com.ssafy.polaris.essay.service;

import java.util.List;

import com.ssafy.polaris.global.SearchConditions;
import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;
import com.ssafy.polaris.global.security.SecurityUser;

public interface EssayService {
	Long writeEssay(EssayRequestDto essay, SecurityUser securityUser);

	EssayResponseDto getEssay(Long essayId);
	void updateEssay(EssayRequestDto essayRequestDto);

	void deleteEssay(Long essayId);

	List<EssayResponseDto> getEssayList(SearchConditions searchConditions);

	void updateHit(Long essayId);

	boolean scrapEssay(Long essayId, SecurityUser securityUser);

	int getScrapCount(Long essayId);
}
