package com.ssafy.polaris.global.exception.handler;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.polaris.global.exception.exceptions.NoBookSelectedException;
import com.ssafy.polaris.global.exception.exceptions.UserBookNotExist;
import com.ssafy.polaris.global.exception.exceptions.category.ForbiddenException;
import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.global.exception.exceptions.category.PolarisRuntimeException;
import com.ssafy.polaris.global.exception.exceptions.UserNotExist;
import com.ssafy.polaris.global.exception.exceptions.WrongArgumentException;
import com.ssafy.polaris.global.exception.exceptions.WrongPasswordException;
import com.ssafy.polaris.global.exception.exceptions.category.AuthenticationException;
import com.ssafy.polaris.global.exception.exceptions.category.PolarisRuntimeException;
import com.ssafy.polaris.global.exception.exceptions.category.UnAuthorizedException;
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
		// TODO: exception이 ErrorCode를 가지도록 하는 것이 어떤가?
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

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnAuthorizedException.class)
	public ErrorResponse authenticationException(PolarisRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(ErrorCode.UNAUTHORIZED);
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ForbiddenException.class)
	public ErrorResponse forbiddenException(PolarisRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(ErrorCode.FORBIDDEN);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AuthenticationException.class)
	public ErrorResponse wrongPasswordException(PolarisRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(ErrorCode.WRONG_PASSWORD);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserNotExist.class)
	public ErrorResponse userNotExistException(PolarisRuntimeException exception) {
		log.error(exception.getMessageKey(), exception, exception.getParams());
		return new ErrorResponse(ErrorCode.USER_NOT_EXIST);
	}

}
