package com.cau.gamehaeduo.domain.duo;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
import com.cau.gamehaeduo.domain.user.UserEntity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuoInfoResponseDTO {
    private int duoId;
    private String nickname;
    private String tier;
    private int price;
    private LocalDateTime requestTime;
    private String duoStatus;
    private boolean reviewWritten;
    private String image;

    public DuoInfoResponseDTO(DuoEntity duo, UserEntity user, PlayerEntity player) {
        this.duoId = duo.getDuoId();
        this.nickname = user.getNickname();
        this.tier = player.getTier();
        this.price = player.getPrice();
        this.requestTime = duo.getRequestTime().toLocalDateTime();
        this.duoStatus = duo.getStatus();
        this.reviewWritten = duo.isReviewWritten();
        this.image = user.getProfilePhotoUrl();
    }
}
