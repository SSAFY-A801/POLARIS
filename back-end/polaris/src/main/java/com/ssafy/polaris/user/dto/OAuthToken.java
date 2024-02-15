package com.ssafy.polaris.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OAuthToken {
	private String access_token;
	private String refresh_token;
}
