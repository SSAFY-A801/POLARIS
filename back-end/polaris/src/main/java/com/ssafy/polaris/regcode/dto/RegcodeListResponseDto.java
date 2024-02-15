package com.ssafy.polaris.regcode.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegcodeListResponseDto {
	List<RegcodeResponseDto> regcodes;
}
