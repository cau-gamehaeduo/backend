package com.cau.gamehaeduo.domain.note;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
import com.cau.gamehaeduo.domain.player.ProfileResponseDTO;
import com.cau.gamehaeduo.domain.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomPlayerProfileDTO extends ProfileResponseDTO {
    private String tier;
    private int price;
    // private DuoStatus duoStatus;


    public RoomPlayerProfileDTO(PlayerEntity duo) {
        super(duo.getUser().getIsPlayer().equals("Y"), duo.getUser().getTop(), duo.getUser().getJungle(),
                duo.getUser().getMid(), duo.getUser().getAd(), duo.getUser().getSupporter());
        this.tier = duo.getTier();
        this.price = duo.getPrice();
    }

    public RoomPlayerProfileDTO(UserEntity user) {
        super(user.getIsPlayer().equals("Y"), user.getTop(), user.getJungle(),
                user.getMid(), user.getAd(), user.getSupporter());
        this.tier = null;
        this.price = -1;
    }
}
