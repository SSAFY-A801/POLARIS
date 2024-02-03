package com.ssafy.polaris.essay.service;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.polaris.common.SearchConditions;
import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;
import com.ssafy.polaris.essay.repository.EssayRepository;
import com.ssafy.polaris.security.SecurityUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true) // select 에 대한 transaction
@Service
public class EssayServiceImpl implements EssayService{

	private final EssayRepository essayRepository;
	private final EntityManager em;

	@Override
	@Transactional
	public EssayResponseDto writeEssay(EssayRequestDto essayRequestDto, SecurityUser securityUser) {
		Essay essay = essayRepository.save(Essay.builder()
			.title(essayRequestDto.getTitle())
			.content(essayRequestDto.getContent())
			.userId(securityUser.getId())
			.userBookId(essayRequestDto.getUserBookId())
			.isOpened(essayRequestDto.getIsOpened())
			.build());
		return EssayResponseDto.builder()
			.id(essay.getId())
			.title(essay.getTitle())
			.content(essay.getContent())
			.user(essay.getUser())
			.userBook(essay.getUserBook())
			.isOpened(essay.getIsOpened())
			.build();
	}

	@Override
	@Transactional
	public EssayResponseDto getEssay(Long essayId) {
		// 에세이를 가져온다.
		Essay essay = essayRepository.findById(essayId)
			.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

		if (essay.getDeletedAt() != null || "비공개".equals(essay.getIsOpened())) {
			throw new RuntimeException("해당 게시글이 존재하지 않습니다.");
		}

		// 에세이 조회수를 올린다.
		essay.updateHit();

		return EssayResponseDto.builder()
			.id(essay.getId())
			.title(essay.getTitle())
			.content(essay.getContent())
			.userBookId(essay.getUserBookId())
			.build();
	}

	@Override
	@Transactional
	public EssayResponseDto updateEssay(EssayRequestDto essayRequestDto) {
		Essay essay = essayRepository.findById(essayRequestDto.getId())
			.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

		if (essay.getDeletedAt() != null) {
			throw new RuntimeException("해당 게시글이 존재하지 않습니다.");
		}

		essay.updateEssay(essayRequestDto);

		return EssayResponseDto.builder()
			.id(essayRequestDto.getId())
			.title(essayRequestDto.getTitle())
			.content(essayRequestDto.getContent())
			.userBookId(essayRequestDto.getUserBookId())
			.isOpened(essayRequestDto.getIsOpened())
			.build();
	}

	@Override
	@Transactional
	public void deleteEssay(Long essayId) {
		Essay essay = essayRepository.findById(essayId)
			.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

		essay.deleteEssay();
	}

	@Override
	public List<EssayResponseDto> getEssayList(SearchConditions searchConditions) {
		searchConditions.setWord(searchConditions.getWord().trim());
		String jpql;
		if (searchConditions.getWord() == null || searchConditions.getWord().equals("")) {
			jpql = "select e.id as id, UserBook as userBook, e.title as title, e.content as content, e.hit as hit\n"
				+ "from Essay e join UserBook ub\n"
				+ "where e.deletedAt is null";
		} else {
			jpql = "select e.id as id, UserBook as userBook, e.title as title, e.content as content, e.hit as hit\n"
				+ "from Essay e join UserBook ub\n"
				+ "where e.deletedAt is null\n"
				+ "    and :key like concat('%', :word,'%')";
		}
		TypedQuery<EssayResponseDto> query = em.createQuery(jpql, EssayResponseDto.class);
		if (searchConditions.getWord() == null || searchConditions.getWord().equals("")) {}
		else {
			query.setParameter("key", searchConditions.getKey())
				.setParameter("word", searchConditions.getWord());
		}
		return query
			.setFirstResult((searchConditions.getPgno() - 1) * searchConditions.getSpp())
			.setMaxResults(searchConditions.getSpp())
			.getResultList();
	}
}
