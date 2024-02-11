package com.ssafy.polaris.essay.service;

import java.util.List;

import com.ssafy.polaris.essay.dto.*;
import com.ssafy.polaris.global.SearchConditions;
import com.ssafy.polaris.global.security.SecurityUser;

public interface EssayService {
	Long writeEssay(EssayRequestDto essay, SecurityUser securityUser);

	EssayResponseDto getEssay(Long essayId);

	void updateEssay(EssayRequestDto essayRequestDto, SecurityUser securityUser);

	void deleteEssay(Long essayId, SecurityUser securityUser);

	List<EssayResponseDto> getEssayList(SearchConditions searchConditions);

	void updateHit(Long essayId);

	boolean scrapEssay(Long essayId, SecurityUser securityUser);

	int getScrapCount(Long essayId);

	List<EssaySimpleResponseDto> getMyEssayView(Long userId);

	MostScrappedEssayResponseDto getMostScrappedEssay();

	List<ScrappedEssayByUserResponseDto> getScrappedEssayByUser(Long userId);
}
