package com.cau.gamehaeduo.domain.base;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    LOGOUT_SUCCESS(true, 1000, "로그아웃했습니다. 안드로이드의 jwtAccessToken과 jwtRefreshToken을 삭제해주세요."),

    /**
     * 2000 : Request 오류
     */
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_ACCESS_JWT(false, 2001, "Access 토큰을 입력해주세요."),
    EMPTY_REFRESH_JWT(false, 2002, "Refresh 토큰을 입력해주세요."),
    INVALID_ACCESS_JWT(false, 2003, "지원되지 않거나 잘못된 Access 토큰 입니다."),
    INVALID_REFRESH_JWT(false, 2012, "지원되지 않거나 잘못된 Refresh 토큰 입니다."),
    INVALID_USER_ACCESS_JWT(false,2005,"Access 토큰의 userIdx와 Request의 userIdx가 일치하지 않습니다."),
    NOT_EXIST_REFRESH_JWT(false,2006,"존재하지 않거나 만료된 Refresh 토큰입니다. 다시 로그인해주세요."),
    EXPIRED_ACCESS_JWT(false,2007,"만료된 Access 토큰입니다. Refresh 토큰을 이용해서 새로운 Access 토큰을 발급 받으세요."),
    EXPIRED_REFRESH_JWT(false,2008,"만료된 Refresh 토큰입니다. 다시 로그인해주세요."),
    EMPTY_USER_IDX(false,2009,"유저 인덱스를 입력해주세요."),


    INVALID_ACCESS_KAKAO(false, 2010, "지원되지 않거나 잘못된 Kakao Access 토큰 입니다."),

    INVALID_REFRESH_KAKAO(false, 2011, "지원되지 않거나 잘못된 Kakao Refresh 토큰 입니다."),
    EXPIRED_ACCESS_KAKAO(false, 2012, "만료된 Kakao Access 토큰입니다. Kakao Refresh 토큰을 이용해서 새로운 Kakao Access 토큰을 발급 받으세요."),
    EXPIRED_REFRESH_KAKAO(false,2013,"만료된 Kakao Refresh 토큰입니다. 다시 로그인해주세요."),


    /**
     * 3000Review
     */
    REVIEW_ALREADY_WRITTEN(false, 3000, "해당 듀오에 대한 리뷰를 이미 작성했습니다."),
    REVIEW_NOT_REQUEST_USER(false, 3001, "듀오를 신청한 사람만 리뷰를 남길 수 있습니다."),

    /**
    * 4000 Player
    */

    PRIVATE_PLAYER_PROFILE(false, 4000, "프로필이 공개되지 않은 유저입니다."),
    SIGNUP_EMPTY_USER_PROFILE(false, 4002, "사진이 없습니다."),
    SIGNUP_INVALID_USER_PROFILE_PHOTO(false, 4002, "유효하지 않은 프로필 사진입니다."),

    /**
     * 5000 SignUP , LogIn
     */
    SIGNUP_ALREADY_EXIST_KAKAO_MEMBER(false,5000, "이미 가입된 회원입니다."),
    SIGNUP_EMPTY_USER_NICKNAME(false, 5001, "닉네임을 입력해주세요."),

    SIGNUP_INVALID_USER_NICKNAME(false, 5002, "닉네임은 영어 또는 한글과 숫자를 조합한 2-10 자리만 가능합니다."),

    SIGNUP_ALREADY_EXIST_NICKNAME(false, 5003, "이미 사용중인 닉네임입니다."),

    LOGIN_INFO_NOT_MATCH(false, 5004, "아이디 비밀번호를 다시 확인해주세요."),
    SIGNUP_EMPTY_USER_ID(false, 5005, "아이디를 입력해주세요."),
    SIGNUP_INVALID_USER_ID(false, 5006, "아이디는 영어와 숫자를 조합한 2-10 자리만 가능합니다."),
    SIGNUP_ALREADY_EXIST_ID(false, 5007, "이미 사용중인 아이디입니다."),
    /**
     * 6000 Message
     */
    NOT_EXIST_NOTE_ROOM(false, 6000, "존재하지 않는 대화방입니다."),

    /**
     * 7000 Duo
     */


    NOT_ENOUGH_POINT(false,7000, "포인트가 부족해 신청할 수 없습니다."),
    NOT_REQUESTED_PLAYER(false, 7001, "듀오를 신청받은 사람만 완료 요청을 할 수 있습니다."),
    DUO_NOT_PROCEEDING(false,7002, "듀오 진행중 상태에만 완료 요청을 할 수 있습니다."),

    DUO_ALREADY_COMPLETE(false,7003, "이미 완료된 듀오입니다."),
    DUO_ALREADY_CANCEL(false,7004, "이미 완료(취소)된 듀오입니다."),
    DUO_NOT_PARTICIPATE_USER(false,7005, "듀오 참가자만 취소 요청을 할 수 있습니다."),
    DUO_ACCEPT_NOT_REQUESTED_USER(false,7006, "듀오 신청받은 사람만 수락을 할 수 있습니다."),
    DUO_NOT_WAITING(false,7007, "듀오가 수락 대기중이 아닙니다."),

    DUO_ALREADY_WAITING(false,7008, "해당 유저에게 이미 듀오 신청 대기중입니다."),
    DUO_ALREADY_PROCEEDING(false,7009, "해당 유저와 이미 듀오 진행중입니다."),


    ;


    private final boolean isSuccess;
    private final int code;
    private final String message;


    BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public static BaseResponseStatus of(final String errorName){
        // valueOf : 이름을 가지고 객체르 가져오는 함수
        return BaseResponseStatus.valueOf(errorName);
    }
}
