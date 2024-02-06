package com.ssafy.polaris.trade.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.polaris.security.SecurityUser;
import com.ssafy.polaris.trade.dto.TradeBookListResponseDto;
import com.ssafy.polaris.trade.dto.TradeBookSelectRequestDto;
import com.ssafy.polaris.trade.response.DefaultResponse;
import com.ssafy.polaris.trade.response.StatusCode;
import com.ssafy.polaris.trade.service.TradeService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/trade")
public class TradeController {
	private final TradeService tradeService;

	/**
	 * 교환 가능 도서 목록 반환
	 * @return
	 */
	@GetMapping(path = "/exchange_books")
	public ResponseEntity<DefaultResponse<TradeBookListResponseDto>> getExchangeBookList(@AuthenticationPrincipal SecurityUser securityUser){
		// TODO : userId 토큰에서 가져오기
		Long userId = securityUser.getId();

		TradeBookListResponseDto tradeBookListResponseDto = tradeService.getExchangeBookList(userId);
		if (tradeBookListResponseDto.getBooks().isEmpty()){
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
	 * 판매 가능 사용자 도서 목록 반환
	 * @return
	 */
	@GetMapping(path = "/purchase_books")
	public ResponseEntity<DefaultResponse<TradeBookListResponseDto>> getPurchaseBookList(@AuthenticationPrincipal SecurityUser securityUser){
		// TODO : userId 토큰에서 가져오기
		Long userId = securityUser.getId();

		TradeBookListResponseDto tradeBookListResponseDto = tradeService.getPurchaseBookList(userId);

		if (tradeBookListResponseDto.getBooks().isEmpty()){
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
	 * @return
	 */
	@PatchMapping("/{chatRoomId}")
	public ResponseEntity<DefaultResponse<Void>> completeTrade(@PathVariable("chatRoomId") Long chatRoomId){
		System.out.println(" controller - complete trade ");
		tradeService.completeTrade(chatRoomId);
		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_COMPLETE_TRADE
		);
	}

	/**
	 * 거래를 취소하거나, 채팅방을 나갑니다.
	 * @param chatRoomId
	 * @return
	 */
	@DeleteMapping("/{chatRoomId}")
	public ResponseEntity<DefaultResponse<Void>> deleteTrade(@PathVariable("chatRoomId") Long chatRoomId){
		System.out.println(" controller - delete trade ");

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
	 * @return
	 */
	@Transactional
	@PostMapping
	public ResponseEntity<DefaultResponse<Void>> selectTradeBook(@RequestBody TradeBookSelectRequestDto request){
		tradeService.selectTradeBooks(request);

		return DefaultResponse.emptyResponse(
			HttpStatus.OK,
			StatusCode.SUCCESS_SELECT_TRADE_USER_BOOK
		);
	}

}
