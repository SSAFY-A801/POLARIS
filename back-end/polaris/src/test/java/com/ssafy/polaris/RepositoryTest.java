package com.ssafy.polaris;

import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.repository.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("getReferenceId test")
	public void get() {
		Long userId = 1L;

		User user = userRepository.getReferenceById(userId);

		Optional<User> user2 = userRepository.findById(userId);

		Assertions.assertThat(user.getId()).isEqualTo(user2.get().getId());

	}
}
