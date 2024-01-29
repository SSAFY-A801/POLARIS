package com.ssafy.polaris.profile.service;

import com.ssafy.polaris.following.dto.FollowDto;
import com.ssafy.polaris.profile.dto.ProfileDto;
import com.ssafy.polaris.profile.response.DefaultResponse;
import com.ssafy.polaris.following.domain.Follow;
import com.ssafy.polaris.following.repository.FollowingRepository;
import com.ssafy.polaris.profile.dto.ProfileRequest;
import com.ssafy.polaris.profile.dto.ProfileResponse;
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

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final EntityManager em;
    private final UserRepository userRepository;
    private final RegcodeRepository regcodeRepository;
    private final FollowingRepository followingRepository;

    // profile view
    // TODO: User 조회 시 no Session 에러가 발생.
    // TODO: 조회 요청에 Transactional 어노테이션이 필요할까? -> 권장 x!
    @Transactional
    public ResponseEntity<DefaultResponse<ProfileResponse>> getProfile(Long userId){
        User findUser = userRepository.getReferenceById(userId);

        if(findUser == null){
            return null;
        }

        System.out.println(findUser.getEmail());
        Regcode reg = regcodeRepository.getReferenceById(findUser.getRegcode().getId());
        int purchaseCnt = userRepository.getTradeCnt(findUser.getId(), TradeStatus.COMPLETED, TradeType.PURCHASE);
        int exchangeCnt = userRepository.getTradeCnt(findUser.getId(), TradeStatus.COMPLETED, TradeType.EXCHANGE);
        ProfileResponse profileResponse = new ProfileResponse(reg,
                findUser.getNickname(),
                findUser.getProfileUrl(),
                findUser.getIntroduction(),
                findUser.getFollowings(),
                purchaseCnt,
                exchangeCnt
        );
        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW, profileResponse);

//        ProfileDto findUser = userRepository.getProfile(userId);
//
//        if(findUser == null){
//            return null;
//        }
//
//        Regcode reg = regcodeRepository.getReferenceById(findUser.getRegcode().getId());
//        int tradingCnt = userRepository.getTradeCnt(userId, TradeStatus.COMPLETED, TradeType.TRADE);
//        int exchangeCnt = userRepository.getTradeCnt(userId, TradeStatus.COMPLETED, TradeType.EXCHANGE);
//        FollowDto followings = followingRepository.getFollowings(userId);
//
//        ProfileResponse profileResponse = new ProfileResponse(reg,
//                findUser.getNickname(),
//                findUser.getProfileUrl(),
//                findUser.getIntroduction(),
//                followings.getFollowings(),
//                tradingCnt,
//                exchangeCnt
//        );
//        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW, profileResponse);
    }

    // Update Profile
    @Override
    public ResponseEntity<DefaultResponse<String>> updateProfile(Long userId, ProfileRequest profileRequest) {
        User findUser = userRepository.getReferenceById(userId);

        if(findUser == null){
            return null;
        }
        Regcode newRegcode = regcodeRepository.getReferenceById(profileRequest.getRegcode().getId());
        findUser.UpdateProfile(newRegcode,
                profileRequest.getNickname(),
                profileRequest.getIntroduction());

        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_UPDATE_USER, "");
    }

    // follow user
    @Override
    @Transactional
    public ResponseEntity<DefaultResponse<String>> followUser(Long userId, Long toFollowUserId){
        User user = userRepository.getReferenceById(userId);
        User toFollowUser = userRepository.getReferenceById(toFollowUserId);
        // JpaRepository에서 가져왔으므로 이미 두 유저는 영속화된 상태이다!
        Follow follow = new Follow();
        System.out.println(em.contains(user)); // true
        System.out.println(em.contains(toFollowUser)); // true

        follow.setFollow(user, toFollowUser);
        System.out.println("user1 : " + follow.getFollower());
        System.out.println("user2 : " + follow.getFollowing());
        System.out.println(user.getFollowings().toString());
        System.out.println(toFollowUser.getFollowers().toString());

        System.out.println(em.contains(follow)); // false

        // TODO: 영속화를 진행하려고 하는데 에러가 뜸!
        followingRepository.save(follow);

        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_FOLLOW_USER, "");
    }
}
