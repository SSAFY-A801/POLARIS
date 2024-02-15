package com.ssafy.polaris.trade.controller;

import com.ssafy.polaris.chat.dto.ChatRoomTradeBookListResponseDto;
import com.ssafy.polaris.chat.service.ChatRoomService;
import com.ssafy.polaris.chat.service.SseService;
import com.ssafy.polaris.trade.dto.ExchangeHistoryResponseDto;
import com.ssafy.polaris.trade.dto.PurchaseHistoryResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.trade.dto.TradeBookListResponseDto;
import com.ssafy.polaris.trade.dto.TradeBookSelectRequestDto;
import com.ssafy.polaris.trade.response.DefaultResponse;
import com.ssafy.polaris.trade.response.StatusCode;
import com.ssafy.polaris.trade.service.TradeService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/trade")
public class TradeController {
	private final TradeService tradeService;
	private final SseService sseService;
	private final ChatRoomService chatRoomService;

	/**
	 * 교환 가능 사용자 도서 목록
	 * @param securityUser
	 * @return userId, books
	 * (id, bookIsbn, title, author, isOpened, isOwned, userBookTradeType, seriesId, seriesName)
	 */
	@GetMapping(path = "/exchange_books")
	public ResponseEntity<DefaultResponse<TradeBookListResponseDto>> getExchangeBookList(
		@AuthenticationPrincipal SecurityUser securityUser) {

		Long userId = securityUser.getId();

		TradeBookListResponseDto tradeBookListResponseDto = tradeService.getExchangeBookList(userId);
		if (tradeBookListResponseDto.getBooks().isEmpty()) {
			return DefaultResponse.emptyResponse(
				HttpStatus.OK,
				StatusCode.SUCCESS_VIEW_EMPTY_TRADE_BOOKS
			);
		}

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_VIEW_TRADE_BOOKS,
			tradeBookListResponseDto
		);
	}

	/**
	 * 판매 가능 사용자 도서 목록
	 * @param securityUser
	 * @return userId, books
	 * (id, bookIsbn, title, author, isOpened, isOwned, userBookTradeType, seriesId, seriesName, userBookPrice)
	 */
	@GetMapping(path = "/purchase_books")
	public ResponseEntity<DefaultResponse<TradeBookListResponseDto>> getPurchaseBookList(
		@AuthenticationPrincipal SecurityUser securityUser) {

		Long userId = securityUser.getId();

		TradeBookListResponseDto tradeBookListResponseDto = tradeService.getPurchaseBookList(userId);

		if (tradeBookListResponseDto.getBooks().isEmpty()) {
			return DefaultResponse.emptyResponse(
				HttpStatus.OK,
				StatusCode.SUCCESS_VIEW_EMPTY_TRADE_BOOKS
			);
		}

		return DefaultResponse.toResponseEntity(
			HttpStatus.OK,
			StatusCode.SUCCESS_VIEW_TRADE_BOOKS,
			tradeBookListResponseDto
		);
	}

	/**
	 * 거래 완료
	 * @param chatRoomId
	 * @return emptyResponse
	 */
	@PatchMapping("/{chatRoomId}")
	public ResponseEntity<DefaultResponse<Void>> completeTrade(@PathVariable("chatRoomId") Long chatRoomId) {
		tradeService.completeTrade(chatRoomId);
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_COMPLETE_TRADE
		);
	}

	/**
	 * 거래를 취소하거나, 채팅방을 나갑니다.
	 * @param chatRoomId
	 * @return emptyResponse
	 */
	@DeleteMapping("/{chatRoomId}")
	public ResponseEntity<DefaultResponse<Void>> deleteTrade(@PathVariable("chatRoomId") Long chatRoomId) {
		tradeService.deleteTrade(chatRoomId);

		// TODO : chatRoomId 가 없는 경우 에러처리
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_DELETE_TRADE
		);
	}

	/**
	 * 채팅방에서 사용자가 도서를 고르고 완료를 누릅니다.
	 * 추가된 도서 리스트와 삭제된 도서 리스트들이 requestDto에 담겨 옵니다.
	 * @param request
	 * @return emptyResponse
	 */
	@Transactional
	@PostMapping
	public ResponseEntity<DefaultResponse<Void>> selectTradeBook(@RequestBody TradeBookSelectRequestDto request) {
		tradeService.selectTradeBooks(request);

		System.out.println("sse - event 발생 시키러 갑니다. "+ request.getChatRoomId());
		ChatRoomTradeBookListResponseDto list = chatRoomService.getChatRoomTradeBookList(request.getChatRoomId());

		// chatRoomId 로 connect 된 곳에 event 발생 시키기
		sseService.sendChangeChatRoomTradeBookList(request.getChatRoomId(), list);

		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_SELECT_TRADE_USER_BOOK
		);
	}

	/**
	 * @param userId 사용자 id
	 * @return 사용자에 해당하는 교환 내역
	 * */
	@GetMapping("/{id}/exchange_history")
	public ResponseEntity<DefaultResponse<Map<String, List<ExchangeHistoryResponseDto>>>> getExchangeHistories(
			@PathVariable("id") Long userId
	){
		List<ExchangeHistoryResponseDto> data = tradeService.getExchangeHistory(userId);
		if(data.isEmpty()){
			return DefaultResponse.emptyResponse(
					HttpStatus.OK,
					StatusCode.SUCCESS_USER_EMPTY_EXCHANGE_HISTORY_VIEW
			);
		}
		return DefaultResponse.toResponseEntity(
				HttpStatus.OK,
				StatusCode.SUCCESS_USER_EXCHANGE_HISTORY_VIEW,
				Map.of("exchangeHistories", data)
		);
	}

	/**
	 * @param userId 사용자 id
	 * @return 사용자에 해당하는 구매 내역
	 * */
	@GetMapping("/{id}/purchase_history")
	public ResponseEntity<DefaultResponse<Map<String, List<PurchaseHistoryResponseDto>>>> getPurchaseHistories(
		@PathVariable("id") Long userId
	){
		List<PurchaseHistoryResponseDto> data = tradeService.getPurchaseHistory(userId);
		if(data.isEmpty()){
			return DefaultResponse.emptyResponse(
					HttpStatus.OK,
					StatusCode.SUCCESS_USER_EMPTY_PURCHASE_HISTORY_VIEW
			);
		}
		return DefaultResponse.toResponseEntity(
				HttpStatus.OK,
				StatusCode.SUCCESS_USER_PURCHASE_HISTORY_VIEW,
				Map.of("purchaseHistories", data)
		);
	}
}
