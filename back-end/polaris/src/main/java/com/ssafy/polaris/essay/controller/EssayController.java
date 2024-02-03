package com.ssafy.polaris.essay.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.common.SearchConditions;
import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;
import com.ssafy.polaris.essay.response.DefaultResponse;
import com.ssafy.polaris.essay.response.StatusCode;
import com.ssafy.polaris.essay.service.EssayService;
import com.ssafy.polaris.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/essay")
@RequiredArgsConstructor
public class EssayController {

	private final EssayService essayService;

	/*
	 * 독후감 글 작성
	 */
	@PostMapping
	public ResponseEntity<DefaultResponse<EssayResponseDto>> createEssay(
		@RequestBody EssayRequestDto essayRequestDto,
		@AuthenticationPrincipal SecurityUser securityUser) {

		EssayResponseDto essayResponseDto = essayService.writeEssay(essayRequestDto, securityUser);

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.ESSAY_WRITE_SUCCESS,
			essayResponseDto
		);
	}

	@GetMapping("/{essayId}")
	public ResponseEntity<DefaultResponse<EssayResponseDto>> getEssay(@PathVariable Long essayId) {
		EssayResponseDto essayResponseDto = essayService.getEssay(essayId);

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.ESSAY_READ_SUCCESS,
			essayResponseDto
		);
	}

	@GetMapping
	public  ResponseEntity getEssayList(@ModelAttribute SearchConditions searchConditions) {
		List<EssayResponseDto> essayResponseDtoList = essayService.getEssayList(searchConditions);
		return null;
	}

	@PatchMapping
	public ResponseEntity<DefaultResponse<EssayResponseDto>> updateEssay(@RequestBody EssayRequestDto essayRequestDto) {
		EssayResponseDto essayResponseDto = essayService.updateEssay(essayRequestDto);
		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.ESSAY_UPDATE_SUCCESS,
			essayResponseDto
		);
	}

	@DeleteMapping
	public ResponseEntity<DefaultResponse<Void>> deleteEssay(@RequestBody Long essayId) {
		essayService.deleteEssay(essayId);
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.ESSAY_DELETE_SUCCESS
		);
	}
}
