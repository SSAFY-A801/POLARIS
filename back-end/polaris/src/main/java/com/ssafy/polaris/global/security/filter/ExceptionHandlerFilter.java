package com.ssafy.polaris.global.security.filter;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.polaris.global.exception.response.ErrorCode;
import com.ssafy.polaris.global.exception.response.ErrorResponse;
import com.ssafy.polaris.user.exception.UserNotAuthorizedException;

import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try{
			filterChain.doFilter(request, response);
		}catch (ExpiredJwtException e){
			//토큰의 유효기간 만료
			setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED ,ErrorCode.EXPIRED_JWT);
		}catch (JwtException | IllegalArgumentException e){
			//유효하지 않은 토큰
			setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, ErrorCode.TOKEN_NOT_VALID);
		}catch (UserNotAuthorizedException e) {
			setErrorResponse(response, ErrorCode.USER_NOT_AUTHORIZED.getStatus(), ErrorCode.USER_NOT_AUTHORIZED);
		}
	}
	private void setErrorResponse(
		HttpServletResponse response,
		int codeNum,
		ErrorCode errorCode
	){
		ObjectMapper objectMapper = new ObjectMapper();
		response.setStatus(codeNum);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ErrorResponse errorResponse = new ErrorResponse(errorCode);
		try{
			response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
		}catch (IOException e){
			e.printStackTrace();
		}
	}
}
