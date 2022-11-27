package com.cau.gamehaeduo.domain.duo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AcceptDuoRequestDTO {
    private int duoIdx;
    private int userIdx;
}
