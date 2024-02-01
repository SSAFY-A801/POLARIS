package com.ssafy.polaris.essay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.polaris.essay.domain.Essay;

@Repository
public interface EssayRepository extends JpaRepository<Essay, Long> {
	Optional<Essay> findById(Long id);
}
