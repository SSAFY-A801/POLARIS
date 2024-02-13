package com.ssafy.polaris.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomParticipantsResponseDto {
	Long chatRoomId;
	Long tradeSenderId;
	Long tradeReceiverId;
	Long senderId;
	Long receiverId;
	String receiverNickname;
	String receiverProfileUrl;
}
