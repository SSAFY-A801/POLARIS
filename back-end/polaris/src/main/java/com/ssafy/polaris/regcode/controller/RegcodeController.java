package com.ssafy.polaris.regcode.controller;

import com.ssafy.polaris.regcode.dto.RegcodeListResponseDto;
import com.ssafy.polaris.regcode.response.DefaultResponse;
import com.ssafy.polaris.regcode.response.StatusCode;
import com.ssafy.polaris.regcode.service.RegcodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/regcode")
public class RegcodeController {
    private final RegcodeService regcodeService;
    @GetMapping("/sido")
    public ResponseEntity<DefaultResponse<RegcodeListResponseDto>> si(){
        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW_SI, regcodeService.si());
    }

    @GetMapping("/gugun")
    public ResponseEntity<DefaultResponse<RegcodeListResponseDto>> gungu(@RequestParam("sido") String sido){
        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW_SI, regcodeService.gungu(sido));
    }

    @GetMapping("/dong")
    public ResponseEntity<DefaultResponse<RegcodeListResponseDto>> dong(@RequestParam("sido") String sido,
                                                                        @RequestParam("gugun") String gugun){
        return DefaultResponse.toResponseEntity(HttpStatus.OK, StatusCode.SUCCESS_VIEW_SI, regcodeService.dong(
                sido,
                gugun
        ));
    }

}
