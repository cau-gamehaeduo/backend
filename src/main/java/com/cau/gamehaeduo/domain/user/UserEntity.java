package com.cau.gamehaeduo.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private int userIdx;
    private String nickname;
    private String profilePhotoUrl;
    private String isPlayer;
    private float rating;
    private String createdAt;
    private String status;
    private int top;
    private int jungle;
    private int mid;
    private int ad;
    private int supporter;
    private long kakaoIdx;


    public UserEntity(String nickname, String profilePhotoUrl, int top, int jungle, int mid, int ad, int supporter, long kakaoIdx) {
        this.nickname = nickname;
        this.profilePhotoUrl = profilePhotoUrl;
        this.top = top;
        this.jungle = jungle;
        this.mid = mid;
        this.ad = ad;
        this.supporter = supporter;
        this.kakaoIdx = kakaoIdx;
    }
}
