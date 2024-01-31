package com.ssafy.polaris.security.util;

import org.springframework.beans.factory.annotation.Value;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;

public class SecurityUtil {
	private static String AUTH_PARAM_NAME = "Authorization";
	private static String TOKEN_PREFIX = "Bearer";

	public static String getAuthParamName() {
		return AUTH_PARAM_NAME;
	}

	public static String getTokenPrefix() {
		return TOKEN_PREFIX;
	}

	public static String getAccessToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTH_PARAM_NAME);
		// TODO : 토큰 키 값에 bearer로 시작하는 지를 검사해야 한다.
		if (StringUtils.isNotEmpty(bearerToken) && StringUtils.isNotBlank(bearerToken)
			&& bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
