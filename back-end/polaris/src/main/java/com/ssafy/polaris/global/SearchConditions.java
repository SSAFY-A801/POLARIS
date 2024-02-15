package com.ssafy.polaris.global;

import lombok.Data;

@Data
public class SearchConditions {
	private Integer pgno;
	private Integer spp;
	private String key;
	private String word;
}
