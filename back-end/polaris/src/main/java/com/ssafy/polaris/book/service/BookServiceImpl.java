package com.ssafy.polaris.book.service;

import com.ssafy.polaris.book.dto.UserBookResponse;
import com.ssafy.polaris.book.repository.UserBookRepository;
import com.ssafy.polaris.book.response.DefaultResponse;
import com.ssafy.polaris.book.response.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final UserBookRepository bookRepository;
    @Override
    public ResponseEntity<DefaultResponse<List<UserBookResponse>>> getUserLibrary(Long userId) {
        List<UserBookResponse> userBooks = bookRepository.findAllByUserId(userId);
        if(userBooks == null){
            return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.FAIL_LIBRARY_VIEW, null);
        }

        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_LIBRARY_VIEW, userBooks);
    }
}
