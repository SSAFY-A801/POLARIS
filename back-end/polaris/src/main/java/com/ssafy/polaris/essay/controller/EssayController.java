package com.ssafy.polaris.essay.controller;

import java.util.List;
import java.util.Map;

import com.ssafy.polaris.comment.dto.CommentReponseDto;
import com.ssafy.polaris.comment.service.CommentService;
import com.ssafy.polaris.essay.dto.MostScrappedEssayResponseDto;
import com.ssafy.polaris.essay.dto.ScrappedEssayByUserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.essay.dto.EssayRequestDto;
import com.ssafy.polaris.essay.dto.EssayResponseDto;
import com.ssafy.polaris.essay.response.DefaultResponse;
import com.ssafy.polaris.essay.response.StatusCode;
import com.ssafy.polaris.essay.service.EssayService;
import com.ssafy.polaris.global.SearchConditions;
import com.ssafy.polaris.global.security.SecurityUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/essay")
@RequiredArgsConstructor
public class EssayController {

	private final EssayService essayService;
	private final CommentService commentService;

	/**
	 * 글 작성
	 * @param essayRequestDto {
	 * 	 "title": String,
	 *   "content": String,
	 *   "userBookId": Long,
	 *   "isOpened": boolean
	 * } 에세이 작성시 필요한 dto
	 * @return DefaultResponse
	 * {
	 *   "status": Integer,
	 *   "message": String,
	 *   "data": EssayResponseDto
	 * }
	 * */
	@PostMapping
	public ResponseEntity<DefaultResponse<EssayResponseDto>> createEssay(
		@RequestBody EssayRequestDto essayRequestDto,
		@AuthenticationPrincipal SecurityUser securityUser) {

		Long essayId = essayService.writeEssay(essayRequestDto, securityUser);
		EssayResponseDto essayResponseDto = essayService.getEssay(essayId);

		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.ESSAY_WRITE_SUCCESS, essayResponseDto);
	}

	/**
	 * 글 조회, 조회수를 자동으로 올린다
	 * @param essayId Long 에세이 ID
	 * @return DefaultResponse
	 * {
	 *   "status": Integer,
	 *   "message": String,
	 *   "data": EssayResponseDto
	 * }
	 * */
	@GetMapping("/{essayId}")
	public ResponseEntity<DefaultResponse<EssayResponseDto>> getEssay(@PathVariable Long essayId) {
		EssayResponseDto essayResponseDto = essayService.getEssay(essayId);
		List<CommentReponseDto> commentList = commentService.getCommentList(essayId);
		essayResponseDto.setComments(commentList);

		essayService.updateHit(essayId);

		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.ESSAY_READ_SUCCESS, essayResponseDto);
	}

	/**
	 * 에세이 목록을 불러온다.
	 * @param searchConditions
	 * {
	 *     "pgno" : int 몇 번째 페이지인지. 1부터 시작,
	 *     "spp" : int 페이지 당 포스트 수,
	 *     "key" : String 겁색 조건,
	 *     "word" : String 검색어
	 * }
	 * @return DefaultResponse
	 * {
	 *   "status": Integer,
	 *   "message": String,
	 *   "data": [
	 *   	EssayResponseDto1,
	 *   	EssayResponseDto2,
	 *   	...
	 *   ]
	 * }
	 * */
	@GetMapping
	public ResponseEntity<DefaultResponse<List<EssayResponseDto>>> getEssayList(
		@ModelAttribute SearchConditions searchConditions) {
		List<EssayResponseDto> essayResponseDtoList = essayService.getEssayList(searchConditions);
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.ESSAY_READ_LIST_SUCCESS, essayResponseDtoList);
	}

	/**
	 * 에세이를 수정한다.
	 * @param essayRequestDto {
	 * 	 "title": String,
	 *   "content": String,
	 *   "userBookId": Long,
	 *   "isOpened": boolean
	 * } 에세이 작성시 필요한 dto
	 * @return DefaultResponse
	 * {
	 *   "status": Integer,
	 *   "message": String,
	 * }
	 * */
	@PatchMapping
	public ResponseEntity<DefaultResponse<EssayResponseDto>> updateEssay(@RequestBody EssayRequestDto essayRequestDto,
		@AuthenticationPrincipal SecurityUser securityUser) {
		essayService.updateEssay(essayRequestDto, securityUser);
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.ESSAY_UPDATE_SUCCESS);
	}

	/**
	 * 에세이를 삭제한다.
	 * @param essayRequestDto {
	 *   "id" : int 에세이 ID
	 * }
	 * @return DefaultResponse
	 * {
	 *   "status": Integer,
	 *   "message": String,
	 * }
	 * */
	@DeleteMapping
	public ResponseEntity<DefaultResponse<Void>> deleteEssay(@RequestBody EssayRequestDto essayRequestDto,
		@AuthenticationPrincipal SecurityUser securityUser) {
		essayService.deleteEssay(essayRequestDto.getId(), securityUser);
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.ESSAY_DELETE_SUCCESS);
	}

	/**
	 * 에세이를 스크랩한다. 토클된다.
	 * @param essayId Long
	 * @return DefaultResponse
	 * {
	 *   "status": Integer,
	 *   "message": String,
	 * }
	 * */
	@PutMapping("/{essayId}")
	public ResponseEntity<DefaultResponse<Void>> scrapEssay(
		@PathVariable("essayId") Long essayId,
		@AuthenticationPrincipal SecurityUser securityUser) {

		boolean isScrapped = essayService.scrapEssay(essayId, securityUser);
		StatusCode returnStatus = isScrapped ? StatusCode.SCRAP_ADD_SUCCESS : StatusCode.SCRAP_REMOVE_SUCCESS;

		return DefaultResponse.emptyResponse(HttpStatus.OK, returnStatus);
	}

	/**
	 * 조회수를 카운트해준다.
	 * @param essayId Long 에세이 ID
	 * @return DefaultResponse
	 * {
	 *   "status": Integer,
	 *   "message": String,
	 *   "data": Integer
	 * }
	 * */
	@GetMapping("/scrap_count/{essayId}")
	public ResponseEntity<DefaultResponse<Integer>> getScrapCount(@PathVariable Long essayId) {
		int scrapCount = essayService.getScrapCount(essayId);

		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SCRAP_COUNT_SUCCESS, scrapCount);
	}
	/**
	 * @return 가장 많이 스크랩된 에세이를 반환
	 * */
	@GetMapping("/most_scrapped")
	public ResponseEntity<DefaultResponse<MostScrappedEssayResponseDto>> getMostScrappedEssay(){
		MostScrappedEssayResponseDto data = essayService.getMostScrappedEssay();
		if(data == null){
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.ESSAY_READ_MOST_SCRAPPED_FAIL);
		}
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.ESSAY_READ_MOST_SCRAPPED_SUCCESS, data);
	}

	/**
	 * @param userId 사용자 id
	 * @return
	 * 	만약 비어있다면 empty response 반환,
	 * 	그게 아니라면 스크랩된 리스트를 반환
	 */
	@GetMapping("/{id}/scraps")
	public ResponseEntity<DefaultResponse<Map<String, List<ScrappedEssayByUserResponseDto>>>> getScrappedEssayList(
			@PathVariable("id") Long userId){
		List<ScrappedEssayByUserResponseDto> data = essayService.getScrappedEssayByUser(userId);
		if(data == null){
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.ESSAY_READ_EMPTY_SCRAPPED_SUCCESS);
		}
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.ESSAY_READ_SCRAPPED_SUCCESS, Map.of("scrapPosts", data));
	}
}
