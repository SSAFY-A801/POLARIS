package com.ssafy.polaris.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.polaris.trade.domain.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long> {
}
