package com.ssafy.polaris.regcode.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.regcode.dto.RegcodeListResponseDto;
import com.ssafy.polaris.regcode.response.DefaultResponse;
import com.ssafy.polaris.regcode.response.StatusCode;
import com.ssafy.polaris.regcode.service.RegcodeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regcode")
public class RegcodeController {
	private final RegcodeService regcodeService;

	/**
	 * @return 시, 도 정보
	 * */
	@GetMapping("/sido")
	public ResponseEntity<DefaultResponse<RegcodeListResponseDto>> getSi() {
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW_SI, regcodeService.si());
	}

	/**
	 * @return 구, 군 정보
	 * */
	@GetMapping("/gugun")
	public ResponseEntity<DefaultResponse<RegcodeListResponseDto>> getGungu(@RequestParam("sido") String sido) {
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW_SI, regcodeService.gungu(sido));
	}

	/**
	 * @return 동 정보
	 * */
	@GetMapping("/dong")
	public ResponseEntity<DefaultResponse<RegcodeListResponseDto>> getDong(@RequestParam("sido") String sido,
		@RequestParam("gugun") String gugun) {
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW_SI, regcodeService.dong(
			sido,
			gugun
		));
	}

}
