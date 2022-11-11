package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.user.CheckNicknameResDTO;
import com.cau.gamehaeduo.domain.user.CreateUserReqDTO;
import com.cau.gamehaeduo.domain.user.CreateUserResDTO;
import com.cau.gamehaeduo.service.KakaoService;
import com.cau.gamehaeduo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Pattern;

import static com.cau.gamehaeduo.domain.base.BaseResponseStatus.*;

@Log4j2
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

    public UserController(UserService userService, KakaoService kakaoService) {
        this.userService = userService;
        this.kakaoService = kakaoService;
    }

    @PostMapping("/signUp")
    public BaseResponse<CreateUserResDTO> createUser (@Valid @RequestBody CreateUserReqDTO createUserReqDTO){
        try {
            // 사용자 생성
            long kakaoIdx = kakaoService.checkKakaoUser(createUserReqDTO.getAccessToken());
            if (kakaoIdx != 0) {
                CreateUserResDTO result = userService.createUser(createUserReqDTO,kakaoIdx);
                return new BaseResponse<>(result);
            }else {
                return new BaseResponse<>(INVALID_ACCESS_KAKAO);
            }

        } catch (BaseException e) {
            log.error(" API : api/signup" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/nickname_dupli")
    public BaseResponse<CheckNicknameResDTO> checkNickname(@RequestParam("nickname") String nickname) {
        // 형식적 validation
        if (nickname == null) {
            return new BaseResponse<>(SIGNUP_EMPTY_USER_NICKNAME);
        }
        else{
            String pattern = "^([가-힣a-zA-Z0-9]{2,10})$";

            if (!Pattern.matches(pattern, nickname)){
                return new BaseResponse<>(SIGNUP_INVALID_USER_NICKNAME);
            }
        }


        try {
            CheckNicknameResDTO result = userService.checkNickname(nickname);
            return new BaseResponse<>(result);
        } catch(BaseException e){
            log.error(" API : api/signup/dupli" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }

    }
}
