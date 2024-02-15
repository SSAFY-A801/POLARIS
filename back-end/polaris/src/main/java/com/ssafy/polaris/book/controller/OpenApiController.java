package com.ssafy.polaris.book.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OpenApiController {
	@Value("${aladinApiConfig.serviceKey}")
	private String serviceKey;

	@Value("${aladinApiConfig.bestSellerCallBackUrl}")
	private String bestSellerCallBackUrl;

	@Value("${aladinApiConfig.searchCallBackUrl}")
	private String searchCallBackUrl;

	/**
	 * 검색 조건 및 키워드에 따라 도서 검색 결과를 반환해주는 API
	 * 
	 * @param query 검색할 타입 ) Title, Author
	 * @param queryType 검색어 (키워드)
	 * @return query 및 query type에 따른 검색 결과   
	 * */
	@GetMapping("/search")
	public ResponseEntity<String> callSearchType(@RequestParam("query") String query,
		@RequestParam("queryType") String queryType) {
		HttpURLConnection urlConnection = null;
		InputStream stream = null;
		String result = null;

		String urlStr = searchCallBackUrl +
			"ttbkey=" + serviceKey +
			"&Query=" + query +
			"&QueryType=" + queryType +
			"&MaxResults=" + "50" +
			"&start=" + "1" +
			"&SearchTarget=" + "Book" +
			"&output=" + "js" +
			"&Version=" + "20131101" +
			"&Cover=" + "Big";

		try {
			URL url = new URL(urlStr);

			urlConnection = (HttpURLConnection)url.openConnection();
			stream = getNetworkConnection(urlConnection);
			result = readStreamToString(stream);

			if (stream != null)
				stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	
	/**
	 * 베스트 셀러를 가져오는 API
	 * 
	 * @return 베스트 셀러 리스트 20개
	 * */
	@GetMapping("/bestseller")
	public ResponseEntity<String> callBestSellerApi() {
		HttpURLConnection urlConnection = null;
		InputStream stream = null;
		String result = null;

		String urlStr = bestSellerCallBackUrl +
			"ttbkey=" + serviceKey +
			"&QueryType=" + "BestSeller" +
			"&MaxResults=" + "20" +
			"&start=" + "1" +
			"&SearchTarget=" + "Book" +
			"&Cover=" + "Big" +
			"&output=" + "js" +
			"&Version=" + "20131101";

		try {
			URL url = new URL(urlStr);

			urlConnection = (HttpURLConnection)url.openConnection();
			stream = getNetworkConnection(urlConnection);
			result = readStreamToString(stream);

			if (stream != null)
				stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	/* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
	private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
		urlConnection.setConnectTimeout(3000);
		urlConnection.setReadTimeout(3000);
		urlConnection.setRequestMethod("GET");
		urlConnection.setDoInput(true);

		if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
		}

		return urlConnection.getInputStream();
	}

	/* InputStream을 전달받아 문자열로 변환 후 반환 */
	private String readStreamToString(InputStream stream) throws IOException {
		StringBuilder result = new StringBuilder();

		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

		String readLine;
		while ((readLine = br.readLine()) != null) {
			result.append(readLine + "\n\r");
		}

		br.close();

		return result.toString();
	}
}
