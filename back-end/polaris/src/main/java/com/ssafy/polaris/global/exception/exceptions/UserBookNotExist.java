package com.ssafy.polaris.global.exception.exceptions;

import com.ssafy.polaris.global.exception.exceptions.category.BadRequestException;

public class UserBookNotExist extends BadRequestException {
	static final String MESSAGE_KEY = "userbook doesn't";

	public UserBookNotExist(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + " : " + detailMessageKey, params);
	}
}
