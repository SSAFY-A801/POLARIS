package com.ssafy.polaris.book.service;

import com.ssafy.polaris.book.dto.UserBookResponse;
import com.ssafy.polaris.book.response.DefaultResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<DefaultResponse<List<UserBookResponse>>> getUserLibrary(Long userId);
}
