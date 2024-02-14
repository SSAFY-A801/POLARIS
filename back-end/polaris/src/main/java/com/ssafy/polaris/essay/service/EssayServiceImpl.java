package com.ssafy.polaris.essay.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ssafy.polaris.essay.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.essay.domain.Scrap;
import com.ssafy.polaris.essay.exception.EssayAlreadyExistException;
import com.ssafy.polaris.essay.repository.EssayRepository;
import com.ssafy.polaris.essay.repository.ScrapRepository;
import com.ssafy.polaris.global.SearchConditions;
import com.ssafy.polaris.global.exception.exceptions.WrongSearchConditionException;
import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.user.exception.UserForbiddenException;
import com.ssafy.polaris.user.exception.UserNotAuthorizedException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true) // select 에 대한 transaction
@Service
public class EssayServiceImpl implements EssayService {

	private final EssayRepository essayRepository;
	private final ScrapRepository scrapRepository;
	private final EntityManager em;
	@Override
	@Transactional
	public Long writeEssay(EssayRequestDto essayRequestDto, SecurityUser securityUser) {
		System.out.println("유저정보 읽어오기" + securityUser.getId());
		Optional<Essay> optionalEssay = essayRepository.findByUserBookIdAndUserId(essayRequestDto.getUserBookId(), securityUser.getId());
		if (optionalEssay.isPresent()) { // 글이 이미 존재
			if (optionalEssay.get().getDeletedAt() == null) {
				throw new EssayAlreadyExistException("");
			}
			else {
				log.info("먼저 작성된 글이 삭제됩니다.====================");
				essayRepository.deleteById(optionalEssay.get().getId());
			}
		}

		Essay essay = essayRepository.save(Essay.builder()
			.title(essayRequestDto.getTitle())
			.content(essayRequestDto.getContent())
			.userId(securityUser.getId())
			.userBookId(essayRequestDto.getUserBookId())
			.isOpened(essayRequestDto.getIsOpened())
			.build());
		return essay.getId();
	}

	@Override
	@Transactional
	public EssayResponseDto getEssay(Long essayId) {
		// 에세이를 가져온다.
		Essay essay = essayRepository.findJoinedEssayById(essayId)
			.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

		// TODO: hit 올린다.
		if (essay.getDeletedAt() != null || !essay.getIsOpened()) {
			throw new RuntimeException("해당 게시글이 존재하지 않습니다.");
		}

		return new EssayResponseDto(essay);
	}

	@Override
	@Transactional
	public void updateHit(Long essayId) {
		essayRepository.findById(essayId)
			.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."))
			.updateHit();
	}

	@Override
	@Transactional
	public void updateEssay(EssayRequestDto essayRequestDto, SecurityUser securityUser) {
		Essay essay = essayRepository.findById(essayRequestDto.getId())
			.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
		if (!essay.getUser().getId().equals(securityUser.getId())) {
			throw new UserForbiddenException("에세이 수정 권한 없음");
		}
		essay.updateEssay(essayRequestDto);
	}

	@Override
	@Transactional
	public void deleteEssay(Long essayId, SecurityUser securityUser) {
		Essay essay = essayRepository.findById(essayId)
			.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
		if (!essay.getUser().getId().equals(securityUser.getId())) {
			throw new UserForbiddenException("에세이 삭제 권한 없음");
		}
		essay.delete();
	}

	@Override
	public List<EssayResponseDto> getEssayList(SearchConditions searchConditions) {
		TypedQuery<Essay> query;
		String jpql = "select e "
			+ "from Essay e "
			+ "	join fetch e.user "
			+ "	left join fetch e.userBook "
			+ " left join fetch e.userBook.book "
			+ "where e.isOpened is true "
			+ "	and e.deletedAt is null ";

		searchConditions.setWord(searchConditions.getWord().trim());
		boolean isNotSearch = searchConditions.getWord() == null || searchConditions.getWord().equals("");
		if (isNotSearch) {
			jpql += " order by e.id desc ";
			query = em.createQuery(jpql, Essay.class);
		} else {
			if (searchConditions.getKey().equals("title")) {
				jpql += " and e.title like concat('%', :word, '%') ";
			} else if (searchConditions.getKey().equals("user")) {
				jpql += " and e.user.nickname like concat('%', :word, '%') ";
			} else if (searchConditions.getKey().equals("bookTitle")) {
				jpql += " and e.userBook.book.title like concat('%', :word, '%') ";
			} else {
				throw new WrongSearchConditionException("");
			}
			jpql += " order by e.id desc ";

			query = em.createQuery(jpql, Essay.class);
			query.setParameter("word", searchConditions.getWord());
		}

		List<Essay> essays = query
			.setFirstResult((searchConditions.getPgno() - 1) * searchConditions.getSpp())
			.setMaxResults(searchConditions.getSpp())
			.getResultList();

		List<EssayResponseDto> essayResponseDtoList = essays.stream()
			.map(EssayResponseDto::new)
			.collect(Collectors.toList());
		return essayResponseDtoList;
	}

	// 리턴값 true : 추가, false : 삭제
	@Override
	@Transactional
	public boolean scrapEssay(Long essayId, SecurityUser securityUser) {
		Optional<Scrap> scrap = scrapRepository.findScrapByEssayIdAndUserID(essayId, securityUser.getId());
		if (scrap.isEmpty()) {
			scrapRepository.saveWithEssayIdAndUserId(essayId, securityUser.getId());
			return true;
		} else {
			return !scrap.get().toggleDeletion();
		}
	}

	@Override
	public int getScrapCount(Long essayId) {
		return scrapRepository.getScrapCount(essayId);
	}

	@Override
	public List<EssaySimpleResponseDto> getMyEssayView(Long userId) {
		List<EssaySimpleResponseDto> data = essayRepository.getEssayByUserId(userId);
		if(data == null){
			return null;
		}
		// scrap 수 넣는 과정
		for(EssaySimpleResponseDto dto : data){
			dto.setScrapCount(scrapRepository.getScrapCount(dto.getEssayId()));
		}

		return data;
	}

	@Override
	public MostScrappedEssayResponseDto getMostScrappedEssay() {
		MostScrappedEssayResponseDto data = essayRepository.getMostScrappedEssay();
		if(data == null){
			return null;
		}
		data.setScrapCount(scrapRepository.getScrapCount(data.getEssayId()));
		return data;
	}

	@Override
	public List<ScrappedEssayByUserResponseDto> getScrappedEssayByUser(Long userId) {
		List<ScrappedEssayByUserResponseDto> data = scrapRepository.getUserScrappedList(userId);
		System.out.println(data);
		if(data.isEmpty()){
			return null;
		}
		return data;
	}
}
