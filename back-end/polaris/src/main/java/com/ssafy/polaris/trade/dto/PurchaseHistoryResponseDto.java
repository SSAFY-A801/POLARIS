package com.ssafy.polaris.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryResponseDto {
    Long tradeId;
    Long senderId;
    Long userBookId;
    String bookTitle;
    Long receiverId;
    Integer price;
    String oppositeNickname;
    LocalDateTime tradeDate;
}
