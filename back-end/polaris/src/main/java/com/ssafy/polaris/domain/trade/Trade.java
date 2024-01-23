package com.ssafy.polaris.domain.trade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.polaris.connectentity.TradeUserBook;
import com.ssafy.polaris.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Trade {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	@Column(name = "trade_type")
	private TradeType tradeType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User sender;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private User receiver;

	@Enumerated(EnumType.STRING)
	private TradeStatus status = TradeStatus.INPROGRESS;

	@OneToMany(mappedBy = "trade")
	private List<TradeUserBook> tradeUserBooks = new ArrayList<>();

	private LocalDateTime createdAt;
	private LocalDateTime finishedAt;

	public void finishTrade(TradeStatus status){
		if(finishedAt != null)
			return;
		this.status = status;
		this.finishedAt = LocalDateTime.now();
	}

	public Trade(TradeType tradeType, User sender, User receiver,
		List<TradeUserBook> tradeUserBooks) {
		createdAt = LocalDateTime.now();
		this.tradeType = tradeType;
		this.sender = sender;
		this.receiver = receiver;
		this.status = TradeStatus.INPROGRESS;
		this.tradeUserBooks = tradeUserBooks;
	}
}
