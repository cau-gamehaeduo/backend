package com.cau.gamehaeduo.domain.player;

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

    public PlayerProfileResponseDTO(boolean isPlayer, String nickname, String tier, int top, int jungle, int mid,
                                    int ad,
                                    int supporter, String playStyle, String introduction, float rating) {
        super(isPlayer, top, jungle, mid, ad, supporter);
        this.nickname = nickname;
        this.tier = tier;
        this.playStyle = playStyle;
        this.introduction = introduction;
        this.rating = rating;
    }
}
