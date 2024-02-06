package com.ssafy.polaris.promotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.polaris.promotion.domain.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

}
