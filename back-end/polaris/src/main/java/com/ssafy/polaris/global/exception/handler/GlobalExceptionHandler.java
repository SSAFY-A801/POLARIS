package com.ssafy.polaris.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.polaris.global.exception.exceptions.NoBookSelectedException;
import com.ssafy.polaris.global.exception.exceptions.UserBookNotExist;
import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.global.exception.exceptions.category.PolarisRuntimeException;
import com.ssafy.polaris.global.exception.exceptions.WrongArgumentException;
import com.ssafy.polaris.global.exception.response.ErrorCode;
import com.ssafy.polaris.global.exception.response.ErrorResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(WrongArgumentException.class)
	public ErrorResponse handlerPolarisRuntimeException(PolarisRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(ErrorCode.WRONG_ARGUMENT);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoBookSelectedException.class)
	public ErrorResponse noBookSelectedHandler(PolarisRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(ErrorCode.NO_BOOK_SELECTED);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UserBookNotExist.class)
	public ErrorResponse userBookNotExistHandler(PolarisRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(ErrorCode.USER_BOOK_NOT_EXIST);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ErrorResponse notFoundHandler(PolarisRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(ErrorCode.NOT_FOUND);
	}
}
