package com.ssafy.polaris.promotion.service;

import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.promotion.dto.PromotionRequestDto;
import com.ssafy.polaris.promotion.dto.PromotionResponseDto;

public interface PromotionService {
	Long createPromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser);

	@Transactional
	PromotionResponseDto getPromotion(Long promotionId, boolean updateHit);

	Long updatePromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser);
}
