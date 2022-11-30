package com.cau.gamehaeduo.domain.review;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllReviewRatingResponseDTO {
    private float totalRating;
    private List<UserReviewDTO> reviews;
}
