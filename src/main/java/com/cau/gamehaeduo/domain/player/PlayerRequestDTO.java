package com.cau.gamehaeduo.domain.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequestDTO {
    private int userIndex;
    private int gender;
    private String introduction;
    private String playStyle;
    private String tier;
    private int price;

    public static PlayerEntity of(PlayerRequestDTO requestDto) {
        return PlayerEntity.builder()
                .id(requestDto.userIndex)  // user index
                .status("A")
                .gender(requestDto.gender)
                .price(requestDto.price)
                .introduction(requestDto.introduction)
                .playStyle(requestDto.playStyle)
                .tier(requestDto.tier)
                .build();
    }
}
