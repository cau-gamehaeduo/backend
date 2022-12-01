package com.cau.gamehaeduo.domain.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateReviewReqDTO {
    int userIdx;
    String reviewText;
    float reviewRating; //0~5
    int duoIdx;
}
