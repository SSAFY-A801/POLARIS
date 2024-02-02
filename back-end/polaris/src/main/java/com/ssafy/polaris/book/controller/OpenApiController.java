package com.ssafy.polaris.book.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600L)
@RequestMapping("/api")
public class OpenApiController {
    // ItemNewAll101Bookxml

    @Value("${aladinApiConfig.serviceKey}")
    private String serviceKey;

    @Value("${aladinApiConfig.callBackUrl}")
    private String callBackUrl;


    @GetMapping("/bestseller")
    public ResponseEntity<String> callBestSellerApi(
    ){
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;

        String urlStr = callBackUrl +
                "ttbkey=" + serviceKey +
                "&QueryType=" + "BestSeller" +
                "&MaxResults=" + "20" +
                "&start=" + "1" +
                "&SearchTarget=" + "Book" +
                "&output=" + "xml" +
                "&Version=" + "20131101";

        try {
            URL url = new URL(urlStr);

            urlConnection = (HttpURLConnection) url.openConnection();
            stream = getNetworkConnection(urlConnection);
            result = readStreamToString(stream);

            if (stream != null) stream.close();
        } catch(IOException e) {
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

        if(urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }

        return urlConnection.getInputStream();
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    private String readStreamToString(InputStream stream) throws IOException{
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String readLine;
        while((readLine = br.readLine()) != null) {
            result.append(readLine + "\n\r");
        }

        br.close();

        return result.toString();
    }
}
