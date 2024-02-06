package com.ssafy.polaris.promotion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.connectentity.domain.PromotionUserBook;
import com.ssafy.polaris.connectentity.repository.PromotionUserBookRepository;
import com.ssafy.polaris.global.exception.exceptions.NoBookSelectedException;
import com.ssafy.polaris.global.exception.exceptions.UserBookNotExist;
import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.promotion.domain.Promotion;
import com.ssafy.polaris.promotion.dto.PromotionRequestDto;
import com.ssafy.polaris.promotion.dto.PromotionResponseDto;
import com.ssafy.polaris.promotion.repository.PromotionRepository;
import com.ssafy.polaris.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true) // select 에 대한 transaction
@Service
public class PromotionServiceImpl implements PromotionService{

	private final PromotionRepository promotionRepository;
	private final PromotionUserBookRepository promotionUserBookRepository;

	@Override
	@Transactional
	public Long createPromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser) {
		if (promotionRequestDto.getUserBookIds().isEmpty())
			throw new NoBookSelectedException("책이 선택되지 않았습니다. (홍보글)");
		// 1. 글을 먼저 만든다.
		Promotion promotion = promotionRepository.save(Promotion.builder()
			.title(promotionRequestDto.getTitle())
			.content(promotionRequestDto.getContent())
			.userId(securityUser.getId())
			.build());
		// 2. 연결을 만든다.
		List<PromotionUserBook> promotionUserBooks = new ArrayList<>();
		for (Long id : promotionRequestDto.getUserBookIds()) {
			promotionUserBooks.add(new PromotionUserBook(id, promotion.getId()));
		}
		try {
			promotionUserBookRepository.saveAll(promotionUserBooks);
		} catch (Exception e) {
			throw new UserBookNotExist("존재하지 않는 사용자 도서입니다. (홍보글)", promotionUserBooks);
		}
		return promotion.getId();
	}

	@Override
	@Transactional
	public PromotionResponseDto getPromotion(Long promotionId) {
		List<PromotionUserBook> userBooksByPromotion = promotionUserBookRepository.getUserBooksByPromotionId(promotionId);
		if (userBooksByPromotion.isEmpty())
			throw new NotFoundException("글을 찾을 수 없습니다. (홍보글)");
		userBooksByPromotion.getFirst().getPromotion().updateHit();

		return new PromotionResponseDto(userBooksByPromotion);
	}
}
