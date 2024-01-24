package com.ssafy.polaris.domain.series;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Series {
	@Id
	private int id;
	private String name;

	public Series(int id, String name) {
		this.id = id;
		this.name = name;
	}
}
