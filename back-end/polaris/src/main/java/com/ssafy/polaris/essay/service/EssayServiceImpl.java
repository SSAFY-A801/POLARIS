package com.ssafy.polaris.essay.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ssafy.polaris.essay.dto.EssaySimpleResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.essay.domain.Scrap;
import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;
import com.ssafy.polaris.essay.repository.EssayRepository;
import com.ssafy.polaris.essay.repository.ScrapRepository;
import com.ssafy.polaris.global.SearchConditions;
import com.ssafy.polaris.global.security.SecurityUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

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

		// TODO: deletedat 없앤다.
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
	public void updateEssay(EssayRequestDto essayRequestDto) {
		Essay essay = essayRepository.findById(essayRequestDto.getId())
			.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

		if (essay.getDeletedAt() != null) {
			throw new RuntimeException("해당 게시글이 존재하지 않습니다.");
		}

		essay.updateEssay(essayRequestDto);
	}

	@Override
	@Transactional
	public void deleteEssay(Long essayId) {
		// Essay essay = essayRepository.findById(essayId)
		// 	.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
		essayRepository.deleteById(essayId);
	}

	@Override
	public List<EssayResponseDto> getEssayList(SearchConditions searchConditions) {
		TypedQuery<Essay> query;
		String jpql;

		searchConditions.setWord(searchConditions.getWord().trim());
		boolean isNotSearch = searchConditions.getWord() == null || searchConditions.getWord().equals("");
		if (isNotSearch) {
			jpql = "select e "
				+ "from Essay e "
				+ "	join fetch e.user "
				+ "	left join fetch e.userBook "
				+ " left join fetch e.userBook.book "
				+ "where e.deletedAt is null";
			query = em.createQuery(jpql, Essay.class);
		} else {
			jpql = "select e "
				+ "from Essay e "
				+ "	join fetch e.user "
				+ "	left join fetch e.userBook "
				+ " left join fetch e.userBook.book "
				+ "where e.deletedAt is null "
				+ "    and e.user.nickname like concat('%', :word,'%')";
			query = em.createQuery(jpql, Essay.class);
			query
				// .setParameter("key", searchConditions.getKey())
				// TODO : 검색 조건에 따른 분기가 필요. (DSL을 쓰지 않는 이상..)
				.setParameter("word", searchConditions.getWord());
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

	//리턴값 true : 추가, false : 삭제
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
}
