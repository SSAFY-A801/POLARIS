package com.ssafy.polaris.series.service;

import java.util.Optional;

import com.ssafy.polaris.series.domain.Series;

public interface SeriesService {
	Optional<Series> getSeriesById(Long seriesId);
}
