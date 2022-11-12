package com.cau.gamehaeduo.domain.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDTO {
    protected boolean isPlayer;
    private int top;
    private int jungle;
    private int mid;
    private int ad;
    private int supporter;
}
