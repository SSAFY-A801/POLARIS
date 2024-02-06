package com.ssafy.polaris.book.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBookListDeleteRequestDto {
    List<UserBookDeleteRequestDto> books;
}
