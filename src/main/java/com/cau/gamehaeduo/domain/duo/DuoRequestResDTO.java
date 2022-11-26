package com.cau.gamehaeduo.domain.duo;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuoRequestResDTO {
    int duoIdx;
    int userIdx;
    int point;
    String status;
}
