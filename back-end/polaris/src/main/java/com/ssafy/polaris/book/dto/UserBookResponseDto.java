package com.ssafy.polaris.book.dto;

import com.ssafy.polaris.book.domain.UserBookTradeType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserBookResponseDto {
    Long id;
    Long userId;
    String cover;
    String title;
    String author;
    String isbn;
    String publisher;
    LocalDateTime pubDate;
    String bookDescription = "";
    String userBookDescription = "";
    Integer priceStandard;
    Integer userBookPrice;
    Boolean isOpened;
    Boolean isOwned;
    UserBookTradeType userBookTradeType;
    Long seriesId;
    String seriesName;
}