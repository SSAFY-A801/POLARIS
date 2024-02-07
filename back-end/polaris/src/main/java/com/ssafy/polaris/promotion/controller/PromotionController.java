package com.ssafy.polaris.promotion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.global.exception.exceptions.category.UnAuthorizedException;
import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.promotion.dto.PromotionRequestDto;
import com.ssafy.polaris.promotion.dto.PromotionResponseDto;
import com.ssafy.polaris.promotion.response.DefaultResponse;
import com.ssafy.polaris.promotion.response.StatusCode;
import com.ssafy.polaris.promotion.service.PromotionService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/promotion")
@RequiredArgsConstructor
public class PromotionController {

	private final PromotionService promotionService;

	@PostMapping
	public ResponseEntity<DefaultResponse<PromotionResponseDto>> createPromotion(
		@RequestBody PromotionRequestDto promotionRequestDto,
		@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null)
			throw new UnAuthorizedException("", PromotionController.class);

		Long promotionId = promotionService.createPromotion(promotionRequestDto, securityUser);
		PromotionResponseDto promotionResponseDto = promotionService.getPromotion(promotionId, false);
		return DefaultResponse.toResponseEntity(HttpStatus.CREATED, StatusCode.PROMOTION_WRITE_SUCCESS, promotionResponseDto);
	}

	@GetMapping("/{promotionId}")
	public ResponseEntity<DefaultResponse<PromotionResponseDto>> getPromotion(@PathVariable("promotionId") Long promotionId) {
		PromotionResponseDto promotionResponseDto = promotionService.getPromotion(promotionId, true);
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.PROMOTION_READ_SUCCESS, promotionResponseDto);
	}

	@PatchMapping
	public ResponseEntity<DefaultResponse<PromotionResponseDto>> updatePromotion(
		@RequestBody PromotionRequestDto promotionRequestDto,
		@AuthenticationPrincipal SecurityUser securityUser) {
		if (securityUser == null)
			throw new UnAuthorizedException("", PromotionController.class);

		Long promotionId = promotionService.updatePromotion(promotionRequestDto, securityUser);
		PromotionResponseDto promotionResponseDto = promotionService.getPromotion(promotionId, false);
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.PROMOTION_UPDATE_SUCCESS, promotionResponseDto);
	}
}
