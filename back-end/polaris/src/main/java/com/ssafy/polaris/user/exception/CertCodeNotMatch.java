package com.ssafy.polaris.user.exception;

import com.ssafy.polaris.global.exception.exceptions.category.BadRequestException;
import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class CertCodeNotMatch extends BadRequestException {
	private static final ErrorCode errorCode = ErrorCode.CERT_CODE_NOT_MATCH;
	public CertCodeNotMatch(String detailMessageKey, Object... params) {
		super(errorCode.getMessage() + " : " + detailMessageKey, errorCode, params);
	}
}
