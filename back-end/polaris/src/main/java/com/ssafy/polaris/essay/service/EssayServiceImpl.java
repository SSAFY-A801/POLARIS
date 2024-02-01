package com.ssafy.polaris.essay.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;
import com.ssafy.polaris.essay.repository.EssayRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EssayServiceImpl implements EssayService{

	private final EssayRepository essayRepository;

	@Override
	public EssayResponseDto writeEssay(EssayRequestDto essayRequestDto) {
		Essay essay = essayRepository.save(Essay.builder()
			.title(essayRequestDto.getTitle())
			.content(essayRequestDto.getContent())
			.userBookId(essayRequestDto.getUserBookId())
			.isOpened(essayRequestDto.getIsOpened())
			.build());
		return EssayResponseDto.builder()
			.id(essay.getId())
			.title(essay.getTitle())
			.content(essay.getContent())
			.userBookId(essay.getUserBookId())
			.isOpened(essay.getIsOpened())
			.build();
	}

	@Override
	public EssayResponseDto getEssay(Long essayId) {
		Essay essay = essayRepository.findById(essayId)
			.orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));

		if ("비공개".equals(essay.getIsOpened())) {
			throw new RuntimeException("해당 게시글이 존재하지 않습니다.");
		}

		return EssayResponseDto.builder()
			.id(essay.getId())
			.title(essay.getTitle())
			.content(essay.getContent())
			.userBookId(essay.getUserBookId())
			.build();
	}
}
