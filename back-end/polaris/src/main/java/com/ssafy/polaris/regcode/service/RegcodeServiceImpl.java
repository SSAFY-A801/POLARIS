package com.ssafy.polaris.regcode.service;

import org.springframework.stereotype.Service;

import com.ssafy.polaris.regcode.dto.RegcodeListResponseDto;
import com.ssafy.polaris.regcode.repository.RegcodeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegcodeServiceImpl implements RegcodeService {
	private final RegcodeRepository regcodeRepository;

	@Override
	public RegcodeListResponseDto si() {
		return new RegcodeListResponseDto(regcodeRepository.getSi());
	}

	@Override
	public RegcodeListResponseDto gungu(String sido) {
		return new RegcodeListResponseDto(regcodeRepository.getGungu(sido));
	}

	@Override
	public RegcodeListResponseDto dong(String sido, String gungu) {
		return new RegcodeListResponseDto(regcodeRepository.getDong(sido, gungu));
	}
}
