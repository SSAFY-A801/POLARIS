package com.ssafy.polaris.profile.controller;

import com.ssafy.polaris.following.dto.FollowListResponseDto;
import com.ssafy.polaris.following.dto.FollowRequestDto;
import com.ssafy.polaris.profile.dto.ProfileRequestDto;
import com.ssafy.polaris.profile.dto.ProfileResponseDto;
import com.ssafy.polaris.profile.response.DefaultResponse;
import com.ssafy.polaris.profile.response.StatusCode;
import com.ssafy.polaris.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    // TODO: ProfileDto Response 만들 때 status, message, data 들어갈 수 있도록 하기!!

    /**
     * @param userId user 식별용 ID
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
        if(profileResponseDto == null){
            return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_VIEW);
        }
        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW, profileResponseDto);
    }

    /**
     * @param userId : user identifier,
     * @param reqDto :
     *     String nickname
     *     Regcode regcode
     *     String introduction
     *     String imageUrl
     * */
    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<Object>> updateProfile(@PathVariable(name="id") Long userId,
                                                            @RequestBody ProfileRequestDto reqDto){

        String retVal = profileService.updateProfile(userId, reqDto);
        System.out.println("update profile hi!!");
        if(retVal == null){
            return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_UPDATE);
        }
        return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCEESS_UPDATE_USER);
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<DefaultResponse<Object>> followUser(@PathVariable("id") Long userId,
                                                              @RequestBody FollowRequestDto data){
        String retVal = profileService.followUser(userId, data.getFollowingId());
        if(retVal == null){
            return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_USER_FOLLOW);
        }
        return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_FOLLOW_USER);
    }

    @GetMapping("/{id}/follow")
    public ResponseEntity<DefaultResponse<FollowListResponseDto>> getFollowingList(@PathVariable("id") Long userId){
        FollowListResponseDto followingList = profileService.getFollowingList(userId);
        if(followingList.getFollowings().isEmpty()){
            return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.FAIL_READ_FOLLOWING_LIST);
        }
        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_READ_FOLLOWING_LIST, followingList);
    }

    @DeleteMapping("/{id}/unfollow")
    public ResponseEntity<DefaultResponse<Object>> unfollow(@PathVariable("id") Long userId,
                                                            @RequestBody FollowRequestDto followRequestDto){
        int deleteVal = profileService.unfollow(userId, followRequestDto.getFollowingId());
        if(deleteVal == 0){
            return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.NOT_FOUND_FOLLOWING_USER);
        }
        return DefaultResponse.emptyResponse(HttpStatus.OK, StatusCode.SUCCESS_UNFOLLOW_USER);
    }
}
