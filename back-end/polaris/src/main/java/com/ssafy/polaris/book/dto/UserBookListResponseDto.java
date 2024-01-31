package com.ssafy.polaris.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserBookListResponseDto {
    List<UserBookResponseDto> books;
}
