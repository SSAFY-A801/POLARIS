package com.ssafy.polaris.chat.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomListResponseDto {
	private Long userId;
	private List<BasicChatRoomResponseDto> chatRoomList;
}
