package com.ssafy.polaris.user.repository;

import com.ssafy.polaris.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> getUserByEmail(String email);
}
