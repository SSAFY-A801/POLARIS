package com.ssafy.polaris.essay.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PutMapping;
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

		Long essayId = essayService.writeEssay(essayRequestDto, securityUser);
		EssayResponseDto essayResponseDto = essayService.getEssay(essayId);

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.ESSAY_WRITE_SUCCESS,
			essayResponseDto
		);
	}

	@GetMapping("/{essayId}")
	public ResponseEntity<DefaultResponse<EssayResponseDto>> getEssay(@PathVariable Long essayId) {
		EssayResponseDto essayResponseDto = essayService.getEssay(essayId);
		essayService.updateHit(essayId);

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.ESSAY_READ_SUCCESS,
			essayResponseDto
		);
	}

	@GetMapping
	public  ResponseEntity getEssayList(@ModelAttribute SearchConditions searchConditions) {
		List<EssayResponseDto> essayResponseDtoList = essayService.getEssayList(searchConditions);
		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.ESSAY_READ_LIST_SUCCESS,
			essayResponseDtoList
		);
	}

	@PatchMapping
	public ResponseEntity<DefaultResponse<EssayResponseDto>> updateEssay(@RequestBody EssayRequestDto essayRequestDto) {
		essayService.updateEssay(essayRequestDto);
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.ESSAY_UPDATE_SUCCESS
		);
	}

	@DeleteMapping
	public ResponseEntity<DefaultResponse<Void>> deleteEssay(@RequestBody EssayRequestDto essayRequestDto) {
		essayService.deleteEssay(essayRequestDto.getId());
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.ESSAY_DELETE_SUCCESS
		);
	}

	@PutMapping("/{essayId}")
	public ResponseEntity<DefaultResponse<Void>> scrapEssay(
		@PathVariable("essayId") Long essayId,
		@AuthenticationPrincipal SecurityUser securityUser) {

		boolean isScrapped = essayService.scrapEssay(essayId, securityUser);
		StatusCode returnStatus = isScrapped ? StatusCode.SCRAP_ADD_SUCCESS : StatusCode.SCRAP_REMOVE_SUCCESS;

		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			returnStatus
		);
	}
}
