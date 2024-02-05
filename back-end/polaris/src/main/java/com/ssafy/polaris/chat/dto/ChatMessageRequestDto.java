package com.ssafy.polaris.chat.dto;

import com.ssafy.polaris.chat.domain.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatMessageRequestDto {
	/**
	 * MessageType : TEXT, IMAGE
	 */
	private MessageType type;
	private Long chatRoomId;
	private Long userId;
	private String nickname;
	private String message;
}
