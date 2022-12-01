package com.cau.gamehaeduo.domain.review;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserReviewDTO {
    private int reviewerId;
    private String nickname;
    private String image;
    private float rating;
    private String reviewContent;
    private LocalDateTime reviewTime;
}
