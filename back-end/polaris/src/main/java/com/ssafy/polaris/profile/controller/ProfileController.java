package com.ssafy.polaris.profile.controller;

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
        ResponseEntity<DefaultResponse<ProfileResponseDto>> retVal = profileService.getProfile(userId);

        System.out.println(retVal.getBody().getData().getNickname());
        if(retVal == null){
            return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.FAIL_USER_VIEW, null);
        }

        return retVal;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<String>> updateProfile(@PathVariable(name="id") Long userId,
                                                            @RequestBody ProfileRequestDto reqDto){

        ResponseEntity<DefaultResponse<String>> retVal = profileService.updateProfile(userId, reqDto);
        System.out.println("update profile hi!!");
        if(retVal == null){
            return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.FAIL_USER_UPDATE, "");
        }

        return retVal;
    }

//    @PostMapping("/{id}/follow")
//    public ResponseEntity<DefaultResponse<String>> followUser(@PathVariable("id") Long userId,
//                                                              @RequestBody FollowDto data){
//        ResponseEntity<DefaultResponse<String>> retVal = profileService.followUser(userId, data.getFollowerUserId());
//        System.out.println("hi follow!");
//        if(retVal == null){
//            return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.FAIL_USER_FOLLOW, "");
//        }
//        return retVal;
//    }

}
