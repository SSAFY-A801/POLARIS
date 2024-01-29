package com.ssafy.polaris.regcode.repository;

import com.ssafy.polaris.regcode.domain.Regcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegcodeRepository extends JpaRepository<Regcode, Long> {
    Regcode getReferenceById(Long id);
}
