package com.ssafy.polaris.following.domain;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.user.domain.User;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.hibernate.Hibernate;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Follow extends BaseEntity {

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "follower_user_id")
	private User follower;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "following_user_id")
	private User following;

	@Transactional
	public void setFollow(User me, User otherPerson){
		this.follower = me;
		this.following = otherPerson;
		
		// 강제 초기화
		Hibernate.initialize(me);
		Hibernate.initialize(otherPerson);

		me.getFollowings().add(this);
		otherPerson.getFollowers().add(this);
	}

}


