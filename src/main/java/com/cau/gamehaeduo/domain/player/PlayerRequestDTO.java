package com.cau.gamehaeduo.domain.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequestDTO {
    private int gender;
    private String status;
    private String introduction;
    private String playStyle;
    private String tier;
    private int price;

    public static PlayerEntity of(PlayerRequestDTO requestDto) {
        return PlayerEntity.builder()
                .status(requestDto.status)
                .gender(requestDto.gender)
                .price(requestDto.price)
                .introduction(requestDto.introduction)
                .playStyle(requestDto.playStyle)
                .tier(requestDto.tier)
                .build();
    }
}
