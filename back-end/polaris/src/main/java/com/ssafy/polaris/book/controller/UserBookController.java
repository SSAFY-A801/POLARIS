package com.ssafy.polaris.book.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.polaris.book.dto.BookRequestDto;
import com.ssafy.polaris.book.dto.UserBookListResponseDto;
import com.ssafy.polaris.book.response.DefaultResponse;
import com.ssafy.polaris.book.service.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return userBookService.getLibrary(userId);
    }

    /**
     * @param userId user identifier
     * @param bookRequestDto 등록할 도서 정보
     *
     * @return 빈 문자열을 가진 ResponseEntity
     * */
    @PostMapping("/{id}/library")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    public ResponseEntity<DefaultResponse<String>> createUserBook(
            @PathVariable("id") Long userId, @RequestBody BookRequestDto bookRequestDto){
        System.out.println("userId: "+ userId);
        return userBookService.createUserBook(userId, bookRequestDto);
    }

}
