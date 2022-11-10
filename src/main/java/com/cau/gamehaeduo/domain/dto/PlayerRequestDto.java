package com.cau.gamehaeduo.domain.dto;

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
}
