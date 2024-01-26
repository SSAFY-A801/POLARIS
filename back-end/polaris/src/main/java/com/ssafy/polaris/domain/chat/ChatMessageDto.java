package com.ssafy.polaris.domain.chat;

import com.ssafy.polaris.domain.chat.domain.MessageType;

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
