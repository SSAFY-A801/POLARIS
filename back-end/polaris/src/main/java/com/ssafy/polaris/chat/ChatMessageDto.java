package com.ssafy.polaris.chat;

import com.ssafy.polaris.chat.domain.MessageType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatMessageDto {
	private String nickname;
	private String content;
	private MessageType type;
}
