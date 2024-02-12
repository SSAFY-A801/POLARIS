package com.ssafy.polaris.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class ProfileRequestDto {
	String nickname;
	Long regcodeId;
	String introduction;
	MultipartFile image;
}


