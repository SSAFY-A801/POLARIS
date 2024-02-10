package com.ssafy.polaris.comment.exception;

import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;
import com.ssafy.polaris.global.exception.response.ErrorCode;

public class CommentNotFoundException extends NotFoundException {
	private static final ErrorCode errorCode = ErrorCode.COMMENT_NOT_FOUND;
	public CommentNotFoundException(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + " : " + detailMessageKey, errorCode, params);
	}
}
