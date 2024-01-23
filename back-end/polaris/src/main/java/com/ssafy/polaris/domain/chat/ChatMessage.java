package com.ssafy.polaris.domain.chat;

import java.time.LocalDateTime;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.domain.trade.Trade;
import com.ssafy.polaris.domain.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class ChatMessage extends BaseEntity {

	private String message;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trade_id")
	private Trade trade;

	public ChatMessage(String message, User user, Trade trade) {
		this.message = message;
		this.user = user;
		this.trade = trade;
	}

	public void deleteChatMessage(LocalDateTime now) {
		setDeletedAt(now);
	}
}