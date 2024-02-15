package com.ssafy.polaris.series.dto;

import org.springframework.stereotype.Component;

import com.ssafy.polaris.series.domain.Series;

@Component
public class SeriesMapper {
	public Series toSeriesEntity(Long seriesId, String seriesName) {
		return Series.builder()
			.id(seriesId)
			.name(seriesName)
			.build();
	}
}
