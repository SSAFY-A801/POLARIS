package com.ssafy.polaris.domain.regcode;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Regcode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String si;

	@Nullable
	private String gungu;

	@Nullable
	private String dong;
}
