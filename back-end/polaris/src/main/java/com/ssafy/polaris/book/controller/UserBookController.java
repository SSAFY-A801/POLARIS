package com.ssafy.polaris.book.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.polaris.book.dto.BookListRequestDto;
import com.ssafy.polaris.book.dto.BookRequestDto;
import com.ssafy.polaris.book.dto.UserBookListResponseDto;
import com.ssafy.polaris.book.dto.UserBookResponseDto;
import com.ssafy.polaris.book.response.DefaultResponse;
import com.ssafy.polaris.book.response.StatusCode;
import com.ssafy.polaris.book.service.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class UserBookController {
    private final UserBookService userBookService;

    /**
     * @param userId user identifier
     * @return 해당 사용자가 가지고 있는 모든 사용자 도서 정보
     * */
    @GetMapping("/{id}/library")
    public ResponseEntity<DefaultResponse<UserBookListResponseDto>> getLibrary(
            @PathVariable("id") Long userId){
        List<UserBookResponseDto> userBookResponseDtos = userBookService.getLibrary(userId);
        if(userBookResponseDtos.isEmpty()){
            return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.NO_CONTENT_IN_LIBRARY_VIEW);
        }
        return DefaultResponse.toResponseEntity(
                    HttpStatus.OK,
                    StatusCode.SUCCESS_LIBRARY_VIEW,
                    new UserBookListResponseDto(userBookResponseDtos)
                );
    }

    /**
     * @param userId user identifier
     * @param bookListRequestDto 등록할 도서 리스트 정보
     *
     * @return 빈 문자열을 가진 ResponseEntity
     * */
    // TODO: 사용자 등록 도서 예외 처리 필요
    @PostMapping("/{id}/library")
    public ResponseEntity<DefaultResponse<String>> createUserBooks(
            @PathVariable("id") Long userId, @RequestBody BookListRequestDto bookListRequestDto){
        userBookService.createUserBook(userId, bookListRequestDto);
        return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_CREATE_USER_BOOK);
    }

    @GetMapping("/{id}/library/{isbn}")
    public ResponseEntity<DefaultResponse<UserBookResponseDto>> getUserBook(@PathVariable("id") Long userId,
                                                                            @PathVariable("isbn") String isbn){
        UserBookResponseDto userBookResponseDto = userBookService.getUserBook(userId, isbn);

        if(userBookResponseDto == null){
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

}
