package com.ssafy.polaris.trade.service;

import java.util.List;

import com.ssafy.polaris.trade.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import com.ssafy.polaris.book.domain.UserBookTradeType;
import com.ssafy.polaris.book.repository.UserBookRepository;
import com.ssafy.polaris.chat.repository.TradeRepository;
import com.ssafy.polaris.connectentity.repository.TradeUserBookRepository;
import com.ssafy.polaris.trade.domain.Trade;
import com.ssafy.polaris.trade.domain.TradeStatus;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
	private final EntityManager em;
	private final UserBookRepository userBookRepository;
	private final TradeRepository tradeRepository;
	private final TradeUserBookRepository tradeUserBookRepository;

	@Override
	public Trade getTradeById(Long chatRoomId) {
		return tradeRepository.getReferenceById(chatRoomId);
	}

	@Override
	public TradeBookListResponseDto getPurchaseBookList(Long userId) {
		List<TradeBookResponseDto> purchaseBookResponseDtoList = userBookRepository.getTradeBookList(userId,
				UserBookTradeType.PURCHASE);

		return new TradeBookListResponseDto(userId, purchaseBookResponseDtoList);
	}

	@Override
	public TradeBookListResponseDto getExchangeBookList(Long userId) {
		List<TradeBookResponseDto> exchangeBookResponseDtoList = userBookRepository.getTradeBookList(userId,
				UserBookTradeType.EXCHANGE);

		return new TradeBookListResponseDto(userId, exchangeBookResponseDtoList);
	}

	@Transactional
	@Override
	public void completeTrade(Long chatRoomId) {
		tradeRepository.completeTrade(chatRoomId, TradeStatus.COMPLETED);
	}

	@Transactional
	@Override
	public void deleteTrade(Long chatRoomId) {
		tradeRepository.deleteById(chatRoomId);
	}

	@Transactional
	@Override
	public void selectTradeBooks(TradeBookSelectRequestDto requestDto) {
		// 추가하는 도서
		List<TradeBookSelectRequestDto.BookDto> addedBooks = requestDto.getAddedBooks();
		// 삭제하는 도서
		List<TradeBookSelectRequestDto.BookDto> deletedBooks = requestDto.getDeletedBooks();

		// 추가
		for (TradeBookSelectRequestDto.BookDto bookDto : addedBooks) {
			tradeUserBookRepository.addTradeUserBook(requestDto.getChatRoomId(), bookDto.getId());
		}

		for (TradeBookSelectRequestDto.BookDto bookDto : deletedBooks) {
			tradeUserBookRepository.deleteTradeUserBook(requestDto.getChatRoomId(), bookDto.getId());
		}
	}

	@Override
	public List<ExchangeHistoryResponseDto> getExchangeHistory(Long userId) {
		List<ExchangeHistoryResponseDto> exchangeHistories = tradeRepository.getExchangeHistory(userId);
		if(exchangeHistories.isEmpty()){
			return null;
		}
		return exchangeHistories;
	}

	@Override
	public List<PurchaseHistoryResponseDto> getPurchaseHistory(Long userId) {
		List<PurchaseHistoryResponseDto> purchaseHistories = tradeRepository.getPurchaseHistory(userId);
		if(purchaseHistories.isEmpty()){
			return null;
		}
		return purchaseHistories;
	}
}
