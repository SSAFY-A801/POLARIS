package com.ssafy.polaris.user.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.ssafy.polaris.user.dto.KakaoProfile;
import com.ssafy.polaris.user.dto.OAuthToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KakaoApi {

	@Value(value = "${kakao.key}")
	private String kakaoApiKey;
	@Value(value = "${kakao.redirectUri}")
	private String kakaoRedirectUri;

	public OAuthToken getOAuthToken(String code) {
		String reqUrl = "https://kauth.kakao.com/oauth/token";

		RestTemplate rt = new RestTemplate();

		//HttpHeader 오브젝트
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded");

		//HttpBody 오브젝트
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", kakaoApiKey);
		params.add("redirect_uri", kakaoRedirectUri);
		params.add("code", code);

		//http 바디(params)와 http 헤더(headers)를 가진 엔티티
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
			new HttpEntity<>(params, headers);

		//reqUrl로 Http 요청 , POST 방식
		ResponseEntity<String> response =
			rt.exchange(reqUrl, HttpMethod.POST, kakaoTokenRequest, String.class);

		String responseBody = response.getBody();

		Gson gson = new Gson();
		OAuthToken oAuthToken = gson.fromJson(responseBody, OAuthToken.class);

		return oAuthToken;
	}

	public KakaoProfile getUserInfo(String accessToken) {
		String reqUrl = "https://kapi.kakao.com/v2/user/me";

		RestTemplate rt = new RestTemplate();

		//HttpHeader 오브젝트
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.add("Content-type", "application/x-www-form-urlencoded");

		//http 헤더(headers)를 가진 엔티티
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
			new HttpEntity<>(headers);

		//reqUrl로 Http 요청 , POST 방식
		ResponseEntity<String> response =
			rt.exchange(reqUrl, HttpMethod.POST, kakaoProfileRequest, String.class);

		KakaoProfile kakaoProfile = new KakaoProfile(response.getBody());

		return kakaoProfile;
	}
}
