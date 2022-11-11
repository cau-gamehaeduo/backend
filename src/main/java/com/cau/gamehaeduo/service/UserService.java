package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.user.CheckNicknameResDTO;
import com.cau.gamehaeduo.domain.user.CreateUserReqDTO;
import com.cau.gamehaeduo.domain.user.CreateUserResDTO;
import com.cau.gamehaeduo.domain.user.UserEntity;
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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 생성
    @Transactional
    public CreateUserResDTO createUser(CreateUserReqDTO createUserReqDTO, long kakaoIdx) throws BaseException {

        // 닉네임 중복확인
        checkNickname(createUserReqDTO.getNickname());

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

        userRepository.createUser(userEntity);
        return new CreateUserResDTO(true, "회원가입에 성공하였습니다.");
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
