package com.ssafy.polaris.chat.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomTradeBookListResponseDto {
	List<ChatRoomTradeBookResponseDto> chatRoomTradeBooks;
}
