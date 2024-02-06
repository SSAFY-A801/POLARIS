package com.ssafy.polaris.global.exception.exceptions;

import com.ssafy.polaris.global.exception.exceptions.category.BadRequestException;

public class NoBookSelectedException extends BadRequestException {
	static final String MESSAGE_KEY = "no book has selected";
	public NoBookSelectedException(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + " : " + detailMessageKey, params);
	}
}
