package com.ssafy.polaris.regcode.dto;

import com.ssafy.polaris.regcode.domain.Regcode;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegcodeListResponseDto {
    List<RegcodeResponseDto> regcodes;
}
