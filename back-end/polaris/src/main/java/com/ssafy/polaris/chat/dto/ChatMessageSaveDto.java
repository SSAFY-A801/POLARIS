package com.ssafy.polaris.chat.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.ssafy.polaris.chat.domain.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageSaveDto implements Serializable {
	/**
	 * MessageType : TEXT, IMAGE
	 */
	private MessageType type;
	private Long chatRoomId;
	private Long userId;
	private String nickname;
	private String createdAt;
	private String message;

	public ChatMessageSaveDto(MessageType type, Long chatRoomId, Long userId, String nickname, LocalDateTime createdAt,
		String message) {
		this.type = type;
		this.chatRoomId = chatRoomId;
		this.userId = userId;
		this.nickname = nickname;
		this.createdAt = String.valueOf(createdAt);
		this.message = message;
	}
}
