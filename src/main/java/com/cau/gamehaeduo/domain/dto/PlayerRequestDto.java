package com.cau.gamehaeduo.domain.dto;

import com.cau.gamehaeduo.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequestDto {
    private int gender;
    private String status;
    private String introduction;
    private String playStyle;
    private String tier;
    private int price;

    public static Player of(PlayerRequestDto requestDto) {
        return Player.builder()
                .status(requestDto.status)
                .gender(requestDto.gender)
                .price(requestDto.price)
                .introduction(requestDto.introduction)
                .playStyle(requestDto.playStyle)
                .tier(requestDto.tier)
                .build();
    }
}
