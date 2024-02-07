package com.ssafy.polaris.series.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.polaris.series.domain.Series;
import com.ssafy.polaris.series.repository.SeriesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeriesServiceImpl implements SeriesService {
	private final SeriesRepository seriesRepository;

	@Override
	public Optional<Series> getSeriesById(Long seriesId) {
		return seriesRepository.findById(seriesId);
	}
}