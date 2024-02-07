package com.ssafy.polaris.global.exception.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * int httpStatus - httpStatus를 숫자로 반환
 * enum errorCode - body로 반환될 string 값과 메시지 string 값
 */
@Data
public class ErrorResponse {
	@NotNull
	private final int status;

	@NotNull
	private final String message;

	/**
	 * @param errorCode - 커스텀 에러 코드
	 */
	public ErrorResponse(ErrorCode errorCode) {
		this.status = errorCode.getStatus();
		this.message = errorCode.getMessage();
	}
}
