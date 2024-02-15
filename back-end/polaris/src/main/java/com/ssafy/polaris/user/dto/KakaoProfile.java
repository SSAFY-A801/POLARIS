package com.ssafy.polaris.user.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class KakaoProfile {
	private Long id;
	private LocalDateTime connectedAt;
	private String email;
	private String nickname;
	private String profileUrl;
	private Long region;

	public KakaoProfile(String jsonResponseBody){
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonResponseBody);

		this.id = element.getAsJsonObject().get("id").getAsLong();

		String connected_at = element.getAsJsonObject().get("connected_at").getAsString();
		connected_at = connected_at.substring(0, connected_at.length() - 1);
		LocalDateTime connectDateTime = LocalDateTime.parse(connected_at, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
		this.connectedAt = connectDateTime;

		this.email = "카카오회원";
		this.nickname = "카카오회원";

		JsonElement property = element.getAsJsonObject().get("properties");
		this.profileUrl = property.getAsJsonObject().get("profile_image").getAsString();
	}

}

