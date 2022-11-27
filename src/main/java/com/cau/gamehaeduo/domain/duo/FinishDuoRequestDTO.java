package com.cau.gamehaeduo.domain.duo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FinishDuoRequestDTO {
    private int duoIdx;
    private int userIdx;
}
