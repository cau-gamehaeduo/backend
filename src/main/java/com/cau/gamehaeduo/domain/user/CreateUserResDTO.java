package com.cau.gamehaeduo.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResDTO {
    private Boolean isCreated;
    private String resultMessage;

    private int userId;
    private String nickname;
    private String profilePhotoUrl;
    private String status;
    private String isPlayer;
    private Long point;

    private String jwtAccessToken;
    private String jwtRefreshToken;
}
