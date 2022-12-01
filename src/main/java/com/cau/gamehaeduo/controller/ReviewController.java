package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.review.AllReviewRatingResponseDTO;
import com.cau.gamehaeduo.service.JwtService;
import com.cau.gamehaeduo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/review")
@RestController
@RequiredArgsConstructor
@Log4j2
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtService jwtService;

    @GetMapping
    public BaseResponse<AllReviewRatingResponseDTO> getUserReviews(@RequestParam("userIdx") int userId,
                                                                   @RequestParam("page") Integer page) {
        try {
            jwtService.validateAccessToken(userId);
            return new BaseResponse<>(reviewService.getUserReview(userId, page));
        } catch (BaseException e) {
            log.error(" API : GET api/review" +"\n Message : " + e.getMessage() +"\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }
}
