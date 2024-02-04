package com.ssafy.polaris.book.controller;

import com.ssafy.polaris.book.dto.*;
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

    /**
     * @param userId 사용자 id
     * @param isbn 도서 식별 번호
     * @return 사용자 도서 상세 정보
     */
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

    /**
     * @param userId 사용자 id
     * @param data 변경할 사용자 도서 정보
     * @return void
     */
    @PatchMapping("/{id}/library")
    public ResponseEntity<DefaultResponse<Void>> updateUserBook(@PathVariable("id") Long userId, @RequestBody UserBookUpdateRequestDto data){
        int result = userBookService.updateUserBook(userId, data);
        if(result == 0){ // 만약 변경하지 못했을 경우 ServiceImpl 에서 0을 리턴
            return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_BOOK_UPDATE);
        }
        return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_USER_BOOK_UPDATE);
    }

    @PutMapping("/{id}/library/{isbn}")
    public ResponseEntity<DefaultResponse<Void>> deleteUserBook(@PathVariable("id") Long userId,
                                                                @PathVariable("isbn") String isbn){
        int result = userBookService.deleteUserBook(userId, isbn);
        if(result == 0){
            // TODO: 삭제하려고 하는 책이 이미 삭제가 된 경우라면? 이런 상황은 발생할 수 없는 것인가?
            return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_BOOK_DELETE);
        }
        return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_USER_BOOK_DELETE);
    }
}
