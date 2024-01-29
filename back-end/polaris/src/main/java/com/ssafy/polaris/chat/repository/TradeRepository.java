package com.ssafy.polaris.chat.repository;

import com.ssafy.polaris.trade.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Integer> {
	// 나의 채팅방 목록
	// List<Trade> findAllByUser(User user);
}
