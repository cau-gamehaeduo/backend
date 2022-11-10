package com.cau.gamehaeduo.domain.base;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {

    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */

    /**
     * 500
     */
    SIGNUP_EMPTY_USER_NICKNAME(false, 5001, "닉네임을 입력해주세요."),
    SIGNUP_INVALID_USER_NICKNAME(false, 5002, "닉네임은 영어 또는 한글과 숫자를 조합한 2-10 자리만 가능합니다."),
    SIGNUP_ALREADY_EXIST_NICKNAME(false, 5003, "이미 사용중인 닉네임입니다."),;

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
