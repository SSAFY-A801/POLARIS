package com.ssafy.polaris.profile.service;

import java.io.IOException;
import java.util.List;

import com.ssafy.polaris.global.s3.service.S3Service;
import org.springframework.stereotype.Service;

import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.following.dto.FollowListResponseDto;
import com.ssafy.polaris.following.dto.FollowRequestDto;
import com.ssafy.polaris.following.dto.FollowResponseDto;
import com.ssafy.polaris.following.repository.FollowingRepository;
import com.ssafy.polaris.profile.dto.ProfileRequestDto;
import com.ssafy.polaris.profile.dto.ProfileResponseDto;
import com.ssafy.polaris.regcode.domain.Regcode;
import com.ssafy.polaris.regcode.repository.RegcodeRepository;
import com.ssafy.polaris.trade.domain.TradeStatus;
import com.ssafy.polaris.trade.domain.TradeType;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
	private final EntityManager em;
	private final UserRepository userRepository;
	private final RegcodeRepository regcodeRepository;
	private final FollowingRepository followingRepository;
	private final S3Service s3Service;

	// profile view
	// TODO: User 조회 시 no Session 에러가 발생.
	// TODO: 조회 요청에 Transactional 어노테이션이 필요할까? -> 권장 x!
	// TODO: 찾는 유저가 없을 때 예외 처리 필요
	@Transactional
	public ProfileResponseDto getProfile(Long userId) {

		User findUser = userRepository.getReferenceById(userId);
		Regcode reg = regcodeRepository.getReferenceById(findUser.getRegcode().getId());
		System.out.println(reg.getId());
		int purchaseCnt = userRepository.getTradeCnt(findUser.getId(), TradeStatus.COMPLETED, TradeType.PURCHASE);
		int exchangeCnt = userRepository.getTradeCnt(findUser.getId(), TradeStatus.COMPLETED, TradeType.EXCHANGE);
		ProfileResponseDto profileResponse = new ProfileResponseDto(
			findUser.getId(),
			reg,
			findUser.getNickname(),
			findUser.getProfileUrl(),
			findUser.getIntroduction(),
			followingRepository.getFollowingCnt(userId),
			purchaseCnt,
			exchangeCnt
		);
		return profileResponse;
	}

	// Update Profile
	// TODO: 찾는 유저가 없을 때 예외 처리 필요
	@Override
	@Transactional
	public String updateProfile(Long userId, ProfileRequestDto profileRequest) throws IOException {
		User findUser = userRepository.getReferenceById(userId);
		Regcode newRegcode = regcodeRepository.getReferenceById(profileRequest.getRegcodeId());
		findUser.UpdateProfile(newRegcode,
			profileRequest.getNickname(),
			profileRequest.getIntroduction(),
			s3Service.uploadFile(userId, profileRequest.getImage()));

		return "";
	}

	// follow user
	@Override
	@Transactional
	public String followUser(Long userId, Long toFollowUserId) {
		User user = userRepository.getReferenceById(userId);
		User toFollowUser = userRepository.getReferenceById(toFollowUserId);
		// JpaRepository에서 가져왔으므로 이미 두 유저는 영속화된 상태이다!
		Follow follow = new Follow(user, toFollowUser);

		System.out.println("user1 : " + follow.getFollower());
		System.out.println("user2 : " + follow.getFollowing());
		System.out.println(user.getFollowings().toString());
		System.out.println(toFollowUser.getFollowers().toString());

		System.out.println(em.contains(follow)); // false

		// TODO: 영속화를 진행하려고 하는데 에러가 뜸!
		followingRepository.save(follow);

		return "";
	}

	@Override
	public FollowListResponseDto getFollowingList(Long userId) {
		System.out.println("userId" + userId);
		List<FollowResponseDto> follows = followingRepository.getFollowingList(userId);

		if (follows == null) {
			return null;
		}

		FollowListResponseDto followingList = new FollowListResponseDto(follows);
		return followingList;
	}

	@Transactional
	public Integer unfollow(Long followerId, List<FollowRequestDto> followRequestDtos) {
		Integer deleteVal = 0;
		for (FollowRequestDto followRequestDto : followRequestDtos) {
			deleteVal += followingRepository.deleteFollowUser(followerId, followRequestDto.getFollowingId());
		}

		return deleteVal;
	}
}
