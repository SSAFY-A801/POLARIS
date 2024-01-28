package com.ssafy.polaris.connectentity;

import com.ssafy.polaris.book.UserBook;
import com.ssafy.polaris.trade.Trade;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class TradeUserBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "trade_id")
	private Trade trade;

	@ManyToOne
	@JoinColumn(name = "user_book_id")
	private UserBook userBook;

	public TradeUserBook(Trade trade, UserBook userBook) {
		this.trade = trade;
		this.userBook = userBook;
	}
}
