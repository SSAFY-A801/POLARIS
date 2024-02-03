package com.ssafy.polaris.essay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.polaris.common.SearchConditions;
import com.ssafy.polaris.essay.domain.Essay;
import com.ssafy.polaris.essay.dto.EssayResponseDto;

@Repository
public interface EssayRepository extends JpaRepository<Essay, Long> {
	Optional<Essay> findById(Long id);
}
