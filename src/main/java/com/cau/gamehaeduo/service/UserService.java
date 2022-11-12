package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.kakao.KakaoMemberCheckResDTO;
import com.cau.gamehaeduo.domain.user.*;
import com.cau.gamehaeduo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.cau.gamehaeduo.domain.base.BaseResponseStatus.SIGNUP_ALREADY_EXIST_NICKNAME;

@Service
@Log4j2
public class UserService {

    private final UserRepository userRepository;
    private final RedisService redisService;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, RedisService redisService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.redisService = redisService;
        this.jwtService = jwtService;
    }

    // 사용자 생성
    @Transactional
    public CreateUserResDTO createUser(CreateUserReqDTO createUserReqDTO, long kakaoIdx) throws BaseException {

        // 닉네임 중복확인
        checkNickname(createUserReqDTO.getNickname());

        // KaKao ID 존재 여부 확인


        //profile 사진 임시 부여
        String profilePhotoUrl = "https://post-phinf.pstatic.net/MjAxOTEwMjVfMjI3/MDAxNTcxOTg2MjI3ODEw.SG1Y3lnjADkcWM78IG2I-Qu_3VA_Hb-c9xbKmYhB3N8g.ZJ-OjMtdudOxaSuGHD9yhPdmsA7uSI--qPQGuWjptxkg.JPEG/image_5814618121571985358109.jpg?type=w1200";


        // createUser 메소드에 넘겨줄 UserEntity 객체 생성
        UserEntity userEntity = new UserEntity(
                createUserReqDTO.getNickname(),
                profilePhotoUrl,
                createUserReqDTO.getTop(),
                createUserReqDTO.getJungle(),
                createUserReqDTO.getMid(),
                createUserReqDTO.getAd(),
                createUserReqDTO.getSupporter(),
                kakaoIdx
        );


        int userId = userRepository.createUser(userEntity);
        // JWT !!!!!
        String jwtAccessToken = jwtService.createAccessToken(userId);
        String jwtRefreshToken = jwtService.createRefreshToken(userId);

        return new CreateUserResDTO(true,
                "회원가입에 성공하였습니다.",
                userId,
                createUserReqDTO.getNickname(),
                profilePhotoUrl,
                "Active",
                "N",
                jwtAccessToken,
                jwtRefreshToken
                );
    }

    public LoginResDTO loginUser(long kakaoIdx) {
        UserLoginInfo userLoginInfo = userRepository.getUserLoginInfo(kakaoIdx);
        // JWT !!!!!
        String jwtAccessToken = jwtService.createAccessToken(userLoginInfo.getUserId());
        String jwtRefreshToken = jwtService.createRefreshToken(userLoginInfo.getUserId());


        return new LoginResDTO(
                "로그인에 성공하였습니다.",
                userLoginInfo.getUserId(),
                userLoginInfo.getNickname(),
                userLoginInfo.getProfilePhotoUrl(),
                userLoginInfo.getStatus(),
                userLoginInfo.getIsPlayer(),
                jwtAccessToken,
                jwtRefreshToken
        );
    }







    //DB에 같은 카카오 ID로 가입되어 있는 회원이 있는지 확인
    public KakaoMemberCheckResDTO checkKakoMember(long kakaoIdx){

        // DB에 KaKao ID 존재 여부 확인
        int isKakaoMembereixsted = 0;

        try{
            isKakaoMembereixsted = userRepository.checkKakaoMember(kakaoIdx);
        }catch (Exception e){
            log.error(e.getMessage());
        }

        if(isKakaoMembereixsted == 0){
            return new KakaoMemberCheckResDTO(false,"회원가입을 시작해주세요.");
        }else{
            return new KakaoMemberCheckResDTO(true,"이미 회원이므로 로그인 해주세요.");
        }

    }





    public CheckNicknameResDTO checkNickname(String nickname) throws BaseException{

        int isNicknameExisted = 0;
        try {
            isNicknameExisted = userRepository.checkNickname(nickname);
        } catch (Exception e) {
            log.error("DATABASE_ERROR when call UserRepository.checkNickname()");
        }

        if (isNicknameExisted == 0) {
            return new CheckNicknameResDTO(false, "사용 가능한 닉네임입니다.");
        } else {
            log.error("ILLEGAL_ARG_ERROR when call UserRepository.checkNickname() because nickname is already used");
            throw new BaseException(SIGNUP_ALREADY_EXIST_NICKNAME);

        }
    }



}
