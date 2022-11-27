package com.cau.gamehaeduo.domain.duo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FinishDuoResDTO {
    private int duoIdx;
    private String status;
    private int point;
}
