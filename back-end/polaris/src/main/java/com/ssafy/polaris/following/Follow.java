package com.ssafy.polaris.following;

import com.ssafy.polaris.common.BaseEntity;
import com.ssafy.polaris.user.User;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Follow extends BaseEntity {

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "follower_user_id")
	private User follower;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "following_user_id")
	private User following;

	@Transactional
	public void setFollow(User me, User otherPerson){
		this.follower = me;
		this.following = otherPerson;

		me.getFollowings().add(this);
		otherPerson.getFollowers().add(this);
	}

}


