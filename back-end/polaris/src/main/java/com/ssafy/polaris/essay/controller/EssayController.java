package com.ssafy.polaris.essay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;
import com.ssafy.polaris.essay.response.DefaultResponse;
import com.ssafy.polaris.essay.response.StatusCode;
import com.ssafy.polaris.essay.service.EssayService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/essay")
@RequiredArgsConstructor
@CrossOrigin
public class EssayController {

	private final EssayService essayService;

	/*
	 * 독후감 글 작성
	 */
	@PostMapping
	public ResponseEntity<DefaultResponse<EssayResponseDto>> createEssay(@RequestBody EssayRequestDto essayRequestDto) {
		EssayResponseDto essayResponseDto = essayService.writeEssay(essayRequestDto);

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

	// put 거의 post 갈아끼움
	@PatchMapping
	public ResponseEntity<DefaultResponse<Void>> updateEssay() {
		// return DefaultResponse.toResponseEntity(
		// 	HttpStatus.OK,
		// 	StatusCode.SUCCESS_LIBRARY_VIEW,
		// 	null
		// );
		return null;
	}

	@DeleteMapping
	public ResponseEntity<DefaultResponse<Void>> deleteEssay() {
		// return DefaultResponse.toResponseEntity(
		// 	HttpStatus.OK,
		// 	StatusCode.SUCCESS_LIBRARY_VIEW,
		// 	null
		// );
		return null;
	}
}
