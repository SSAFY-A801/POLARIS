package com.ssafy.polaris.global.exception.exceptions.category;

import com.ssafy.polaris.global.exception.response.ErrorCode;

/**
 * 415
 * 지원하지 않는 파일 형태.
 * ContentType, Content Encoding 데이터를 확인할 필요가 있다
 */
public class UnsupportedMediaTypeException extends PolarisRuntimeException {
	protected static String MESSAGE_KEY = "error.UnsupportedMediaTypeException";
	public UnsupportedMediaTypeException(String detailMessageKey, ErrorCode errorCode, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, errorCode, params);
	}
}
