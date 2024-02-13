package com.ssafy.polaris.global.security.filter;

import java.io.IOException;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.ssafy.polaris.global.security.SecurityUser;
import com.ssafy.polaris.global.security.provider.JwtTokenProvider;
import com.ssafy.polaris.global.security.util.SecurityUtil;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.exception.UserNotAuthorizedException;
import com.ssafy.polaris.user.exception.UserNotFoundException;
import com.ssafy.polaris.user.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

// TODO : OncePerRequestFilter 필터 적용가능한 지 후에 검사
// 잘 모르겠지만 다른 범주?의 필터가 아닌가 함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final StringRedisTemplate redisTemplate;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {
		// 1. 토큰 추출
		String accessToken = SecurityUtil.getAccessToken((HttpServletRequest)request);

		// 2. 유효성 검사
		// 로그아웃 된 토큰은 없는취급
		if (Boolean.FALSE.equals(redisTemplate.hasKey("blackList:" + accessToken))) {
			if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
				// access Token의 경우
				if (accessToken.length() > 150) {
					Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

					User user = userRepository.findUserByEmail(authentication.getName())
						.orElseThrow(() -> new UserNotFoundException(""));

					SecurityUser securityUser = new SecurityUser(user);

					Authentication customAuthentication = new UsernamePasswordAuthenticationToken(
						securityUser, "", securityUser.getAuthorities()
					);

					// securityContext에 전역 저장
					SecurityContextHolder.getContext().setAuthentication(customAuthentication);
				} else {
					// refresh 에 대한 검사를 여기에 추가할 수 있다.
				}
			}
		}
		chain.doFilter(request, response);
	}
}
