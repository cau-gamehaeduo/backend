package com.cau.gamehaeduo.domain.player;

import com.cau.gamehaeduo.domain.user.UserEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequestDTO {
    private int userIdx;
    private int gender;
    private String introduction;
    private String playStyle;
    private String tier;
    private int price;

    public static PlayerEntity of(PlayerRequestDTO requestDto, UserEntity user) {
        return PlayerEntity.builder()
                .id(requestDto.userIdx)  // user index
                .status("A")
                .gender(requestDto.gender)
                .price(requestDto.price)
                .introduction(requestDto.introduction)
                .playStyle(requestDto.playStyle)
                .tier(requestDto.tier)
                .user(user)
                .registeredAt(LocalDateTime.now())
                .build();
    }
}
