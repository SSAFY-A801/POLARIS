package com.ssafy.polaris.profile.service;
import com.ssafy.polaris.book.repository.UserBookRepository;
import com.ssafy.polaris.following.dto.FollowListResponseDto;
import com.ssafy.polaris.following.dto.FollowResponseDto;
import com.ssafy.polaris.profile.dto.ProfileRequestDto;
import com.ssafy.polaris.profile.dto.ProfileResponseDto;
import com.ssafy.polaris.profile.response.DefaultResponse;
import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.following.repository.FollowingRepository;
import com.ssafy.polaris.profile.response.StatusCode;
import com.ssafy.polaris.regcode.domain.Regcode;

import com.ssafy.polaris.regcode.repository.RegcodeRepository;
import com.ssafy.polaris.trade.domain.TradeStatus;
import com.ssafy.polaris.trade.domain.TradeType;
import com.ssafy.polaris.user.domain.User;
import com.ssafy.polaris.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final EntityManager em;
    private final UserRepository userRepository;
    private final RegcodeRepository regcodeRepository;
    private final FollowingRepository followingRepository;
    private final UserBookRepository userBookRepository;

    // profile view
    // TODO: User 조회 시 no Session 에러가 발생.
    // TODO: 조회 요청에 Transactional 어노테이션이 필요할까? -> 권장 x!
    @Transactional
    public ResponseEntity<DefaultResponse<ProfileResponseDto>> getProfile(Long userId) {
        User findUser = userRepository.getReferenceById(userId);

        if (findUser == null) {
            return null;
        }

        System.out.println(findUser.getEmail());
        Regcode reg = regcodeRepository.getReferenceById(findUser.getRegcode().getId());
        int purchaseCnt = userRepository.getTradeCnt(findUser.getId(), TradeStatus.COMPLETED, TradeType.PURCHASE);
        int exchangeCnt = userRepository.getTradeCnt(findUser.getId(), TradeStatus.COMPLETED, TradeType.EXCHANGE);
        ProfileResponseDto profileResponse = new ProfileResponseDto(reg,
                findUser.getNickname(),
                findUser.getProfileUrl(),
                findUser.getIntroduction(),
                followingRepository.getFollowingCnt(userId),
                purchaseCnt,
                exchangeCnt
        );
        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW, profileResponse);
    }

    // Update Profile
    @Override
    @Transactional
    public ResponseEntity<DefaultResponse<String>> updateProfile(Long userId, ProfileRequestDto profileRequest) {
        User findUser = userRepository.getReferenceById(userId);

        if(findUser == null){
            return null;
        }
        Regcode newRegcode = regcodeRepository.getReferenceById(profileRequest.getRegcode().getId());
        findUser.UpdateProfile(newRegcode,
                profileRequest.getNickname(),
                profileRequest.getIntroduction(),
                profileRequest.getImageUrl());

        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_UPDATE_USER, "");
    }

    // follow user
    @Override
    @Transactional
    public ResponseEntity<DefaultResponse<String>> followUser(Long userId, Long toFollowUserId){
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

        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_FOLLOW_USER, "");
    }

    @Override
    public ResponseEntity<DefaultResponse<FollowListResponseDto>> getFollowingList(Long userId) {
        List<FollowResponseDto> follows = followingRepository.getFollowingList(userId);

        FollowListResponseDto followingList = new FollowListResponseDto(follows);

        if(follows.isEmpty()){
            return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.FAIL_USER_VIEW, null);
        }

        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_READ_FOLLOWING_LIST, followingList);
    }
}
