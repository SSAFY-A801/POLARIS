package com.ssafy.polaris.promotion.service;

import com.ssafy.polaris.promotion.dto.PromotionRequestDto;
import com.ssafy.polaris.promotion.dto.PromotionResponseDto;
import com.ssafy.polaris.security.SecurityUser;

public interface PromotionService {
	Long createPromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser);

	PromotionResponseDto getPromotion(Long promotionId);
}
