package com.cau.gamehaeduo.domain.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlayerListDTO {
    private List<HomePartnerDTO> homePartnerDTO;
}
