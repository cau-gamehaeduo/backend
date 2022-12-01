package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.review.AllReviewRatingResponseDTO;
import com.cau.gamehaeduo.domain.review.ReviewEntity;
import com.cau.gamehaeduo.domain.review.UserReviewDTO;
import com.cau.gamehaeduo.domain.user.UserEntity;
import com.cau.gamehaeduo.repository.ReviewRepository;
import com.cau.gamehaeduo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private static final int PAGE_SIZE = 10;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public AllReviewRatingResponseDTO getUserReview(int userId, int page) {
        float totalRating = userRepository.getPlayerRating(userId);
        UserEntity user = userRepository.selectByUserId(userId);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("reviewTime").descending());
        Page<ReviewEntity> reviews = reviewRepository.findByRevieweeId(user, pageable);
        List<UserReviewDTO> reviewDTOS = new ArrayList<>();
        for(ReviewEntity review : reviews) {
            UserEntity reviewUser = review.getWriterId();
            reviewDTOS.add(
                    UserReviewDTO.builder()
                            .reviewerId(reviewUser.getUserIdx())
                            .nickname(reviewUser.getNickname())
                            .image(reviewUser.getProfilePhotoUrl())
                            .rating(review.getRating())
                            .reviewContent(review.getReviewContent())
                            .reviewTime(review.getReviewTime())
                            .build()
            );
        }
        return new AllReviewRatingResponseDTO(totalRating, reviewDTOS);
    }
}
