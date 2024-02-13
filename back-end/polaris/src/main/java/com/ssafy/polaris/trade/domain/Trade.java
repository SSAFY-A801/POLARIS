package com.ssafy.polaris.trade.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.ssafy.polaris.connectentity.domain.TradeUserBook;
import com.ssafy.polaris.user.domain.User;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Trade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "trade_type")
	private TradeType tradeType;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private User sender;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private User receiver;

	@NotNull
	@Builder.Default
	@Enumerated(EnumType.STRING)
	private TradeStatus status = TradeStatus.INPROGRESS;

	@OneToMany(mappedBy = "trade" , cascade = CascadeType.REMOVE)
	private List<TradeUserBook> tradeUserBooks = new ArrayList<>();

	@NotNull
	@CreationTimestamp
	private LocalDateTime createdAt;

	private LocalDateTime finishedAt;

	public void finishTrade(TradeStatus status) {
		if (finishedAt != null)
			return;
		this.status = status;
		this.finishedAt = LocalDateTime.now();
	}
}
