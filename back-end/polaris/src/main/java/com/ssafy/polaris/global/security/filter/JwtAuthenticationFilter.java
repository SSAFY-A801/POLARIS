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
		// TODO : 로그아웃을 먼저 검사해야 할 지도??
		// TODO : isJWT 등을 만들어서 빈 토큰, 잘못된 토큰 검사를 먼저 한다.
		// TODO : refresh 토큰의 경우 reissue로 항상 가는 것이므로 통과시켜준다.
		// TODO : 빈 토큰이 아닐 때 expired 인지 검사를 한다!!!
		if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
			if (accessToken.length() > 150) {
				if (redisTemplate.hasKey("blackList:" + accessToken).booleanValue())
					throw new UserNotAuthorizedException("로그아웃 된 토큰입니다.");

				Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

				User user = userRepository.findUserByEmail(authentication.getName())
					.orElseThrow(() -> new UserNotFoundException(""));

				SecurityUser securityUser = new SecurityUser(user);

				Authentication customAuthentication = new UsernamePasswordAuthenticationToken(
					securityUser, "", securityUser.getAuthorities()
				);

				// securityContext에 전역 저장
				SecurityContextHolder.getContext().setAuthentication(customAuthentication);
			}
			else {
				// refresh 에 대한 검사를 여기에 추가할 수 있다.
			}
		}
		chain.doFilter(request, response);
	}
}
