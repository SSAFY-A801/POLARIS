package com.ssafy.polaris.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserSetPasswordDto {
	String oldPassword;
	String newPassword;
}
