package com.ssafy.polaris.domain.chat;

import java.time.LocalDateTime;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.domain.trade.Trade;
import com.ssafy.polaris.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends BaseEntity {

	@Column(length = 3000)
	private String message;

	@Enumerated(EnumType.STRING)
	private MessageType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trade_id")
	private Trade trade;

	public void deleteChatMessage(LocalDateTime now) {
		setDeletedAt(now);
	}
}