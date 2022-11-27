package com.cau.gamehaeduo.domain.duo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CancelDuoRequestDTO {
    private int duoIdx;
    private int userIdx;
}
