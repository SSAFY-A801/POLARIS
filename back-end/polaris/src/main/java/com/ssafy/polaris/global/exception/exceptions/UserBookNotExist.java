package com.ssafy.polaris.global.exception.exceptions;

import com.ssafy.polaris.global.exception.exceptions.category.NotFoundException;

public class UserBookNotExist extends NotFoundException {
	static final String MESSAGE_KEY = "사용자 도서를 찾을 수 없습니다.";

	public UserBookNotExist(String detailMessageKey, Object... params) {
		super(MESSAGE_KEY + " : " + detailMessageKey, params);
	}
}
