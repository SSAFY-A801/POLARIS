package com.ssafy.polaris.promotion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.connectentity.domain.PromotionUserBook;
import com.ssafy.polaris.connectentity.repository.PromotionUserBookRepository;
import com.ssafy.polaris.global.SearchConditions;
import com.ssafy.polaris.global.exception.exceptions.NoBookSelectedException;
import com.ssafy.polaris.global.exception.exceptions.UserBookNotExist;
import com.ssafy.polaris.global.exception.exceptions.category.ForbiddenException;
import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.promotion.domain.Promotion;
import com.ssafy.polaris.promotion.dto.PromotionListResponseDto;
import com.ssafy.polaris.promotion.dto.PromotionRequestDto;
import com.ssafy.polaris.promotion.dto.PromotionResponseDto;
import com.ssafy.polaris.promotion.repository.PromotionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true) // select 에 대한 transaction
@Service
public class PromotionServiceImpl implements PromotionService{

	private final EntityManager em;
	private final PromotionRepository promotionRepository;
	private final PromotionUserBookRepository promotionUserBookRepository;

	@Override
	@Transactional
	public Long createPromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser) {
		if (promotionRequestDto.getUserBookIds().isEmpty())
			throw new NoBookSelectedException("책이 선택되지 않았습니다. (홍보글)", PromotionServiceImpl.class);
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
			throw new UserBookNotExist("존재하지 않는 사용자 도서입니다. (홍보글)", PromotionServiceImpl.class);
		}
		return promotion.getId();
	}

	@Override
	@Transactional
	public PromotionResponseDto getPromotion(Long promotionId, boolean updateHit) {
		Promotion promotion = promotionRepository.getJoinedPromotionById(promotionId)
			.orElseThrow(() -> new NotFoundException("글을 찾을 수 없습니다. (홍보글)", PromotionServiceImpl.class));
		if (updateHit)
			promotion.updateHit();

		return new PromotionResponseDto(promotion);
	}

	@Override
	@Transactional
	public Long updatePromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser) {
		Promotion promotion = promotionRepository.getJoinedPromotionById(promotionRequestDto.getId())
			.orElseThrow(() -> new NotFoundException("글을 찾을 수 없습니다. (홍보글)", PromotionServiceImpl.class));

		if (!promotion.getUserId().equals(securityUser.getId()))
			throw new ForbiddenException("금지된 요청입니다. (홍보글 수정)", PromotionServiceImpl.class);

		promotionUserBookRepository.deleteAll(promotion.getPromotionUserBooks());
		promotion.update(promotionRequestDto);
		try {
			promotionUserBookRepository.saveAll(promotion.getPromotionUserBooks());
		} catch (Exception e) {
			throw new UserBookNotExist("홍보글", PromotionServiceImpl.class);
		}

		return promotion.getId();
	}

	@Override
	@Transactional
	public void deletePromotion(PromotionRequestDto promotionRequestDto, SecurityUser securityUser) {
		Promotion promotion = promotionRepository.getJoinedPromotionById(promotionRequestDto.getId())
			.orElseThrow(() -> new NotFoundException("글을 찾을 수 없습니다. (홍보글)", PromotionServiceImpl.class));

		if (!promotion.getUserId().equals(securityUser.getId()))
			throw new ForbiddenException("금지된 요청입니다. (홍보글 수정)", PromotionServiceImpl.class);

		promotionUserBookRepository.deleteAll(promotion.getPromotionUserBooks());
		promotionRepository.deleteById(promotion.getId());
	}

	@Override
	public List<PromotionListResponseDto> getPromotionList(SearchConditions searchConditions) {
		TypedQuery<PromotionListResponseDto> query = null;
		String jpql;

		searchConditions.setWord(searchConditions.getWord().trim());
		boolean isNotSearch = searchConditions.getWord() == null || searchConditions.getWord().equals("");
		if (isNotSearch) {
			jpql = "select new com.ssafy.polaris.promotion.dto.PromotionListResponseDto("
				+ "u.id, "
				+ "u.profileUrl, "
				+ "u.nickname, "
				+ "concat(r.si, ' ', r.gungu, ' ', r.dong), "
				+ "p.id, "
				+ "p.title, "
				+ "p.content, "
				+ "(select sum(pub.userBook.userBookPrice) from PromotionUserBook as pub where pub.promotion = p), "
				+ "f.isDeleted, "
				+ "(select count(*) from Favorite as f where f.promotion = p and f.isDeleted is false) "
				+ ") "
				+ "from Promotion as p  "
				+ "    left join User as u on u.id = p.user.id "
				+ "    left join Favorite as f on f.promotion.id = p.id "
				+ "	   join Regcode as r on u.regcode.id = r.id ";
			query = em.createQuery(jpql, PromotionListResponseDto.class);
		} else {
				// TODO : 검색 조건에 따른 분기가 필요. (DSL을 쓰지 않는 이상..)
		}

		List<PromotionListResponseDto> promotions = query
			.setFirstResult((searchConditions.getPgno() - 1) * searchConditions.getSpp())
			.setMaxResults(searchConditions.getSpp())
			.getResultList();

		return promotions;
		// System.out.println(promotions.get(0).getUser().getRegcode().getDong());
	}

}
