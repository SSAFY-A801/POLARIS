package com.ssafy.polaris.series.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.polaris.series.domain.Series;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
