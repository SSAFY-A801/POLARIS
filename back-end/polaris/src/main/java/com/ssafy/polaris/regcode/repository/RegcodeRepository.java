package com.ssafy.polaris.regcode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ssafy.polaris.regcode.domain.Regcode;
import com.ssafy.polaris.regcode.dto.RegcodeResponseDto;

public interface RegcodeRepository extends JpaRepository<Regcode, Long> {
	Regcode getReferenceById(Long id);

	@Query("select new com.ssafy.polaris.regcode.dto.RegcodeResponseDto(MIN(r.id), r.si) from Regcode r " +
		"group by r.si")
	List<RegcodeResponseDto> getSi();

	@Query("select new com.ssafy.polaris.regcode.dto.RegcodeResponseDto(MIN(r.id), r.gungu) from Regcode r " +
		"where r.si = :si and r.gungu not like '' group by r.gungu")
	List<RegcodeResponseDto> getGungu(@Param("si") String si);

	@Query("select new com.ssafy.polaris.regcode.dto.RegcodeResponseDto(MIN(r.id), r.dong) from Regcode r " +
		"where r.si like %:si% and r.gungu like %:gungu% and r.dong not like '' group by r.dong ")
	List<RegcodeResponseDto> getDong(@Param("si") String si, @Param("gungu") String gungu);
}
