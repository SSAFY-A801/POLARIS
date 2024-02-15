package com.ssafy.polaris.essay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScrappedEssayByUserResponseDto {
    Long essayId;
    String essayTitle;
    Long writeId;
    String writerNickname;
    String bookTitle;
    LocalDateTime createdAt;
}
