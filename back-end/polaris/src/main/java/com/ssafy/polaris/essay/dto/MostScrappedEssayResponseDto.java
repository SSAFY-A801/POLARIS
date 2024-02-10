package com.ssafy.polaris.essay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MostScrappedEssayResponseDto {
    String cover;
    String author;
    String bookTitle;
    Integer scrapCount;
    String bookDescription;
    Long userId;
    String nickname;
    Long essayId;
}
