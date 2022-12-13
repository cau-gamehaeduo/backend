package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.kakao.KakaoMemberCheckResDTO;
import com.cau.gamehaeduo.domain.kakao.KakaoUserValidReqDTO;
import com.cau.gamehaeduo.domain.user.*;
import com.cau.gamehaeduo.service.KakaoService;
import com.cau.gamehaeduo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
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
    public BaseResponse<CreateUserResDTO> createUser (@RequestBody CreateUserReqDTO createUserReqDTO){
        try {
            // 사용자 생성
            long kakaoIdx = kakaoService.checkKakaoUser(createUserReqDTO.getAccessToken());
            KakaoMemberCheckResDTO kakaoMemberCheckResDTO = userService.checkKakoMember(kakaoIdx);
            if(kakaoMemberCheckResDTO.getIsMember()==true){
                return new BaseResponse<>(SIGNUP_ALREADY_EXIST_KAKAO_MEMBER);
            }

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


    @PostMapping("/login")
    public BaseResponse<LoginResDTO> login(@RequestBody KakaoUserValidReqDTO kakaoUserValidReqDTO){
        try{
            long kakaoIdx = kakaoService.checkKakaoUser(kakaoUserValidReqDTO.getAccessToken());
            LoginResDTO result = userService.loginUser(kakaoIdx);
            log.info(" API : api/login 호출 \n" + "카카오 로그인 할거면 나한테 연락하라 했는데 안하고 하는 사람들!");
            return new BaseResponse<>(result);

        }catch (BaseException e){
            log.error(" API : api/login" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }


    //id-pw 방식으로 회원가입 및 로그인
    @PostMapping("/signUp-id")
    public BaseResponse<CreateUserResDTO> createIdUser (@RequestBody CreateIdUserReqDTO createIdUserReqDTO){
        try {

            CreateUserResDTO result = userService.createIdUser(createIdUserReqDTO);
            return new BaseResponse<>(result);

        } catch (BaseException e) {
            log.error(" API : api/signup" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

    @PostMapping("/login-id")
    public BaseResponse<LoginResDTO> loginId (@RequestBody LoginIdReqDTO loginIdReqDTO){
        try{
            LoginResDTO result = userService.loginIdUser(loginIdReqDTO);
            return new BaseResponse<>(result);
        }catch (BaseException e){
            log.error(" API : api/login" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }


    //id 중복 체크
    @GetMapping("/id/dupli")
    public BaseResponse<CheckNicknameResDTO> checkId(@RequestParam("id") String id) {
        // 형식적 validation
        if (id == null) {
            return new BaseResponse<>(SIGNUP_EMPTY_USER_ID);
        }
        else{
            String pattern = "^([a-zA-Z0-9]{2,10})$";

            if (!Pattern.matches(pattern, id)){
                return new BaseResponse<>(SIGNUP_INVALID_USER_ID);
            }
        }

        try {
            CheckNicknameResDTO result = userService.checkId(id);
            return new BaseResponse<>(result);
        } catch(BaseException e){
            log.error(" API : api/id/dupli" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }

    }




    @PostMapping("/userCheck")
    public BaseResponse<KakaoMemberCheckResDTO> userCheck(@RequestBody KakaoUserValidReqDTO kakaoUserValidReqDTO){
        try{
            long kakaoIdx = kakaoService.checkKakaoUser(kakaoUserValidReqDTO.getAccessToken());
            if(kakaoIdx != 0 ){
                KakaoMemberCheckResDTO result = userService.checkKakoMember(kakaoIdx);
                return new BaseResponse<>(result);
            }else {
                return new BaseResponse<>(INVALID_ACCESS_KAKAO);
            }
        }catch (BaseException e){
            log.error(" API : api/userCheck" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }


    @GetMapping("/nickname/dupli")
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
            log.error(" API : api/nickname/dupli" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }

    }
}
