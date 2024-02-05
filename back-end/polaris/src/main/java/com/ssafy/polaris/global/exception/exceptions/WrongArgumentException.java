package com.ssafy.polaris.global.exception.exceptions;

import com.ssafy.polaris.global.exception.exceptions.category.BadRequestException;

/**
 * 커스텀 예외 생성 방법.
 * 1. 중분류 예외를 선택하여 extend
 * 1-1. 상위 예외의 생성자를 호출하는 생성자 필요. message는 서버의 에러로그에 남는다.
 * 2. ErrorCode에 적절한 에러코드 생성. Body로 반환되는 부분.
 * 3. GlobalExceptionHandler에 커스텀 예외를 핸들링 하는 메서드 작성
 * 3-1. ResponseStatus 어노테이션은 실제로 반환될 에러 코드. 가능하면 맞춰주자.
 * 3-2. ErrorResponse에 적절한 코드를 넣어 반환하면 body 완성!
 *
 * 0. exceptionTestController를 남겨두겠습니다. 연습해보셔도 좋아요!
 *
 * 기대 효과
 * - 서버 로그에 에러 로그 남김.
 * - 바디를 ErrorCode로 간단히 구현
 * - 에러 코드 HttpStatus 또한 커스텀 가능!
 */
public class WrongArgumentException extends BadRequestException {
	static final String MESSAGE_KEY = "wrong-argument";
	public WrongArgumentException(String detailMessageKey, Object[] params) {
		super(MESSAGE_KEY + " : " + detailMessageKey, params);
	}
}
