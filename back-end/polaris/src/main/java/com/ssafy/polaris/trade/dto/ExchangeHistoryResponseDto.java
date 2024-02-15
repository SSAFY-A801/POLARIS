package com.ssafy.polaris.trade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeHistoryResponseDto {
    Long tradeId;
    Long userId;
    String nickname;
    Long userBookId;
    String bookTitle;
    LocalDateTime tradeDate;
}