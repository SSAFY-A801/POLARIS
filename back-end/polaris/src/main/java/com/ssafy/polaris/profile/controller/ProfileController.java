package com.ssafy.polaris.profile.controller;

import com.ssafy.polaris.essay.dto.EssaySimpleResponseDto;
import com.ssafy.polaris.essay.service.EssayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.polaris.following.dto.FollowListRequestDto;
import com.ssafy.polaris.following.dto.FollowListResponseDto;
import com.ssafy.polaris.following.dto.FollowRequestDto;
import com.ssafy.polaris.profile.dto.ProfileRequestDto;
import com.ssafy.polaris.profile.dto.ProfileResponseDto;
import com.ssafy.polaris.profile.response.DefaultResponse;
import com.ssafy.polaris.profile.response.StatusCode;
import com.ssafy.polaris.profile.service.ProfileService;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
	private final ProfileService profileService;
	private final EssayService essayService;
	// TODO: ProfileDto Response 만들 때 status, message, data 들어갈 수 있도록 하기!!

	/**
	 * @param userId 사용자 id
	 * @return ProfileDto
	 * {
	 *   "status": Integer,
	 *   "message": String,
	 *   "data": Object,
	 * }
	 * */
	@GetMapping("/{id}")
	public ResponseEntity<DefaultResponse<ProfileResponseDto>> getProfile(@PathVariable("id") Long userId) {
		ProfileResponseDto profileResponseDto = profileService.getProfile(userId);
		if (profileResponseDto == null) {
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_VIEW);
		}
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW, profileResponseDto);
	}

	/**
	 * @param userId 사용자 id,
	 * @param reqDto 업데이트할 사용자 정보
	 *     String nickname
	 *     Regcode regcode
	 *     String introduction
	 *     String imageUrl
	 * */
	@PatchMapping("/{id}")
	public ResponseEntity<DefaultResponse<Void>> updateProfile(@PathVariable(name = "id") Long userId,
		@RequestBody ProfileRequestDto reqDto) {

		String retVal = profileService.updateProfile(userId, reqDto);
		System.out.println("update profile hi!!");
		if (retVal == null) {
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_UPDATE);
		}
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCEESS_UPDATE_USER);
	}

	/**
	 * @param userId 사용자 id
	 * @param data 팔로잉할 사용자 id
	 * @return void
	 */
	@PostMapping("/{id}/follow")
	public ResponseEntity<DefaultResponse<Void>> followUser(@PathVariable("id") Long userId,
		@RequestBody FollowRequestDto data) {
		System.out.println(data.getFollowingId());
		String retVal = profileService.followUser(userId, data.getFollowingId());
		if (retVal == null) {
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_FOLLOW);
		}
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_FOLLOW_USER);
	}

	/**
	 * @param userId 팔로잉 리스트를 조회할 유저의 id
	 * @return 팔로잉하는 유저의 리스트
	 */
	@GetMapping("/{id}/follow")
	public ResponseEntity<DefaultResponse<FollowListResponseDto>> getFollowingList(@PathVariable("id") Long userId) {
		FollowListResponseDto followingList = profileService.getFollowingList(userId);
		if (followingList.getFollowings() == null) {
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_READ_FOLLOWING_LIST);
		}
		return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_READ_FOLLOWING_LIST, followingList);
	}

	/**
	 * @param userId 언팔로우 작업을 수행할 나의 id
	 * @param data 언팔로우할 유저들을 담고 있는 리스트
	 * @return
	 */
	@DeleteMapping("/{id}/unfollow")
	public ResponseEntity<DefaultResponse<Void>> unfollow(@PathVariable("id") Long userId,
		@RequestBody FollowListRequestDto data) {
		int deleteVal = profileService.unfollow(userId, data.getUnfollowings());
		if (deleteVal == 0) {
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.NOT_FOUND_FOLLOWING_USER);
		}
		return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_UNFOLLOW_USER);
	}

	/**
	 * @param userId 사용자 id
	 * @return 내가 쓴 독후감 리스트들 반환   
	 * */
	@GetMapping("/{id}/essay")
	public ResponseEntity<DefaultResponse<Map<String, List<EssaySimpleResponseDto>>>> getMyEssay(@PathVariable("id") Long userId){
		List<EssaySimpleResponseDto> data = essayService.getMyEssayView(userId);

		if(data == null){
			return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_READ_MY_ESSAYS);
		}
		return DefaultResponse.toResponseEntity(
				HttpStatus.OK,
				StatusCode.SUCCESS_READ_MY_ESSAYS,
				Map.of("myEssays", data)
				);
	}
}
