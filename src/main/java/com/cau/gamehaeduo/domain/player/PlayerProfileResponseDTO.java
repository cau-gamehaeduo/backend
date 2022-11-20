package com.cau.gamehaeduo.domain.player;

import com.cau.gamehaeduo.domain.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerProfileResponseDTO extends ProfileResponseDTO{
    private String nickname;
    private String tier;
    private String playStyle;
    private String introduction;
    private float rating;

    public PlayerProfileResponseDTO(UserEntity user, PlayerEntity player) {
        super(user.getIsPlayer().equals("Y"), user.getTop(), user.getJungle(), user.getMid(), user.getAd(), user.getSupporter());
        this.nickname = user.getNickname();
        this.tier = player.getTier();
        this.playStyle = player.getPlayStyle();
        this.introduction = player.getIntroduction();
        this.rating = user.getRating();
    }
}