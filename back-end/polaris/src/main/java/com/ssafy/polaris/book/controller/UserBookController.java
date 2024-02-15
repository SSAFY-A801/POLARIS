package com.ssafy.polaris.book.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.book.dto.BookListRequestDto;
import com.ssafy.polaris.book.dto.SearchUserBookListResponseDto;
import com.ssafy.polaris.book.dto.UserBookListDeleteRequestDto;
import com.ssafy.polaris.book.dto.UserBookListResponseDto;
import com.ssafy.polaris.book.dto.UserBookResponseDto;
import com.ssafy.polaris.book.dto.UserBookUpdateRequestDto;
import com.ssafy.polaris.book.dto.WeeklyBooksDto;
import com.ssafy.polaris.book.response.DefaultResponse;
import com.ssafy.polaris.book.response.StatusCode;
import com.ssafy.polaris.book.service.UserBookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class UserBookController {
	private final UserBookService userBookService;

	/**
	 * 유저의 서재에 있는 도서 리스트를 가져오는 API
	 *
	 * @param userId 사용자 id
	 * @return 해당 사용자가 가지고 있는 모든 사용자 도서 정보
	 * */
	@GetMapping("/{id}/library")
	public ResponseEntity<DefaultResponse<UserBookListResponseDto>> getLibrary(
		@PathVariable("id") Long userId) {
		List<UserBookResponseDto> userBookResponseDtos = userBookService.getLibrary(userId);
		if (userBookResponseDtos.isEmpty()) {
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.NO_CONTENT_IN_LIBRARY_VIEW);
		}
		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_LIBRARY_VIEW,
			new UserBookListResponseDto(userBookResponseDtos)
		);
	}

	/**
	 * 검색된 도서 정보를 통해 사용자 도서를 생성하는 API
	 * 도서는 알라딘 API에서 검색된 도서이고, 사용자 도서는 사용자가 가지고 있는 도서이다.
	 * 여러 명이 같은 도서를 가질 수 있기에 도서의 isbn과 사용자의 id로 사용자 도서를 구분한다.
	 *
	 * @param userId user identifier
	 * @param bookListRequestDto 등록할 도서 리스트 정보
	 *
	 * @return 빈 문자열을 가진 ResponseEntity
	 * */
	@PostMapping("/{id}/library")
	public ResponseEntity<DefaultResponse<String>> createUserBooks(
		@PathVariable("id") Long userId, @RequestBody BookListRequestDto bookListRequestDto) {
		userBookService.createUserBook(userId, bookListRequestDto);
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_CREATE_USER_BOOK);
	}

	/**
	 * 사용자의 id와 isbn을 이용해 사용자 도서를 가져오는 API
	 *
	 * @param userId 사용자 id
	 * @param isbn 도서 식별 번호
	 * @return 사용자 도서 상세 정보
	 */
	@GetMapping("/{id}/library/{isbn}")
	public ResponseEntity<DefaultResponse<UserBookResponseDto>> getUserBook(@PathVariable("id") Long userId,
		@PathVariable("isbn") String isbn) {
		UserBookResponseDto userBookResponseDto = userBookService.getUserBook(userId, isbn);

		if (userBookResponseDto == null) {
			return DefaultResponse.emptyResponse(
				HttpStatus.OK,
				StatusCode.FAIL_USER_BOOK_VIEW
			);
		}
		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_USER_BOOK_VIEW,
			userBookResponseDto
		);
	}

	/**
	 * 사용자 도서의 상태를 업데이트 시켜주는 API
	 *
	 * @param userId 사용자 id
	 * @param data 변경할 사용자 도서 정보
	 * @return void
	 */
	@PutMapping("/{id}/library")
	public ResponseEntity<DefaultResponse<Void>> updateUserBook(@PathVariable("id") Long userId,
		@RequestBody UserBookUpdateRequestDto data) {
		int result = userBookService.updateUserBook(userId, data);
		if (result == 0) { // 만약 변경하지 못했을 경우 ServiceImpl 에서 0을 리턴
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_BOOK_UPDATE);
		}
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_USER_BOOK_UPDATE);
	}

	/**
	 * 사용자의 서재에서 도서를 삭제해주는 API
	 *
	 * @param userId 사용자 id
	 * @param data 삭제할 사용자 등록 도서의 id들
	 * @return void
	 *
	 * */
	@DeleteMapping("/{id}/library")
	public ResponseEntity<DefaultResponse<Void>> deleteUserBook(@PathVariable("id") Long userId,
		@RequestBody UserBookListDeleteRequestDto data) {
		int result = userBookService.deleteUserBook(userId, data);
		if (result == 0) {
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_BOOK_DELETE);
		}
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_USER_BOOK_DELETE);
	}

	/**
	 * 지역 정보, 검색 조건 및 키워드로 사용자 도서를 검색하는 API.
	 * 검색 조건에는 Title, Author 가 있다.
	 * 
	 * @param queryType 검색 조건
	 * @param keyword 검색어
	 * @return 조건에 따라 검색된 것들 리턴
	 * */
	@GetMapping("/search")
	public ResponseEntity<DefaultResponse<SearchUserBookListResponseDto>> searchByConditionUserBook(
		@RequestParam(value = "regcode", required = false) Long regcodeId,
		@RequestParam(value = "queryType", required = false) String queryType,
		@RequestParam(value = "keyword", required = false) String keyword
	) {
		SearchUserBookListResponseDto data = userBookService.searchByConditionUserBook(regcodeId, queryType, keyword);
		if (data == null) {
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_SEARCH_USER_BOOK);
		}
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_SEARCH_USER_BOOK, data);
	}
	
	/**
	 * 사용자의 도서에 가장 많이 담긴 20권의 도서를 가져오는 API이다.
	 * Redis에 저장된 도서를 캐싱해 가져온다.
	 *
	 * @return 사용자의 도서에 가장 많이 담긴 20권의 도서
	 * */
	@GetMapping("/popular_books")
	public ResponseEntity<DefaultResponse<List<WeeklyBooksDto>>> getWeeklyUserBook() {
		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_WEEKLY_USER_BOOK_VIEW,
			userBookService.getCachedWeeklyBooks()
		);
	}

	/**
	 * 사용자 인기 도서를 생성해주는 API, 테스트 용
	 * */
	@GetMapping("/popular_books/create")
	public ResponseEntity<DefaultResponse<Void>> createWeeklyUserBook() {
		userBookService.saveWeeklyBooks();
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_WEEKLY_USER_BOOK_CREATE
		);
	}
}
