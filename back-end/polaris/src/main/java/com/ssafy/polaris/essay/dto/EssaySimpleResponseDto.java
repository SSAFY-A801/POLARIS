package com.ssafy.polaris.essay.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EssaySimpleResponseDto {
    Long essayId;
    String essayTitle;
    String bookTitle;
    int hit;
    int scrapCount;
    LocalDateTime createdAt;
}
