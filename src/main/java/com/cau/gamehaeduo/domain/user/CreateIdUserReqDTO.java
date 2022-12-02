package com.cau.gamehaeduo.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIdUserReqDTO {

    @NotBlank(message = "EMPTY_ACCESS_TOKEN")
    private String accessToken;

    @NotBlank(message = "EMPTY_NICKNAME")
    @Pattern(regexp = "^([ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]{2,10})$", message = "SIGNUP_INVALID_USER_NICKNAME")
    private String nickname;


    @NotNull
    private int top;
    @NotNull
    private int jungle;
    @NotNull
    private int mid;
    @NotNull
    private int ad;
    @NotNull
    private int supporter;

    @NotNull
    private String id;
    @NotNull
    private String password;

}
