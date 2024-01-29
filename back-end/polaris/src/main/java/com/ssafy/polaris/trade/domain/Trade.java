package com.ssafy.polaris.trade.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.polaris.trade.TradeStatus;
import com.ssafy.polaris.trade.TradeType;
import com.ssafy.polaris.user.domain.User;
import org.hibernate.annotations.CreationTimestamp;

import com.ssafy.polaris.connectentity.TradeUserBook;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "trade_type")
	private TradeType tradeType;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User sender;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private User receiver;

	@NotNull
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private TradeStatus status = TradeStatus.INPROGRESS;

	@OneToMany(mappedBy = "trade")
	private List<TradeUserBook> tradeUserBooks = new ArrayList<>();

	@NotNull
	@CreationTimestamp
	private LocalDateTime createdAt;

	private LocalDateTime finishedAt;

	public void finishTrade(TradeStatus status){
		if(finishedAt != null)
			return;
		this.status = status;
		this.finishedAt = LocalDateTime.now();
	}
}
