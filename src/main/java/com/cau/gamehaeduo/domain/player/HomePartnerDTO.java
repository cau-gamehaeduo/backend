package com.cau.gamehaeduo.domain.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HomePartnerDTO {
    private int playerId;
    private int price;
    private String userNickname;
    private String profilePhotoUrl;
    private String tier;
    private float rating;
}
