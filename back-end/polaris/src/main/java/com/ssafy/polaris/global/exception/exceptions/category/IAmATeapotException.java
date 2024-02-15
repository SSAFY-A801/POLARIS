package com.ssafy.polaris.global.exception.exceptions.category;

import com.ssafy.polaris.global.exception.response.ErrorCode;

public class IAmATeapotException extends PolarisRuntimeException {
	protected static String MESSAGE_KEY = "error.I'mATeapot";
	public IAmATeapotException(String detailMessageKey, ErrorCode errorCode, Object... params) {
		super(MESSAGE_KEY + "." + detailMessageKey, errorCode, params);
	}
}
