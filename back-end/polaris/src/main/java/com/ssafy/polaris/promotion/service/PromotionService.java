package com.ssafy.polaris.promotion.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.global.SearchConditions;
import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.promotion.dto.PromotionListResponseDto;
import com.ssafy.polaris.promotion.dto.PromotionRequestDto;
import com.ssafy.polaris.promotion.dto.PromotionResponseDto;

public interface PromotionService {
	Long createPromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser);

	PromotionResponseDto getPromotion(Long promotionId, boolean updateHit);

	Long updatePromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser);

	void deletePromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser);

	List<PromotionListResponseDto> getPromotionList(SearchConditions searchConditions);

	boolean favoritePromotion(Long promotionId, SecurityUser securityUser);

	int getFavoriteCount(Long promotionId);
}
