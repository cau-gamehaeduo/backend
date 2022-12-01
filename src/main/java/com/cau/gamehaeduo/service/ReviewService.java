package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.duo.DuoEntity;
import com.cau.gamehaeduo.domain.review.*;
import com.cau.gamehaeduo.domain.user.UserEntity;
import com.cau.gamehaeduo.repository.DuoRepository;
import com.cau.gamehaeduo.repository.ReviewRepository;
import com.cau.gamehaeduo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.cau.gamehaeduo.domain.base.BaseResponseStatus.REVIEW_ALREADY_WRITTEN;
import static com.cau.gamehaeduo.domain.base.BaseResponseStatus.REVIEW_NOT_REQUEST_USER;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService {
    private static final int PAGE_SIZE = 10;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final DuoRepository duoRepository;

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

    public CreateReviewResDTO createReview(CreateReviewReqDTO createReviewReqDTO) throws BaseException {
        //리뷰 작성 되어 있는지 검증
        DuoEntity duoEntity = duoRepository.findByDuoId(createReviewReqDTO.getDuoIdx());
        if(duoEntity.isReviewWritten() == true){
            throw new BaseException(REVIEW_ALREADY_WRITTEN);
        }

        //듀오 요청한 사람인지 검증
        if(duoEntity.getRequestUserId().getUserIdx() != createReviewReqDTO.getUserIdx()){
            throw new BaseException(REVIEW_NOT_REQUEST_USER);
        }

        ReviewEntity reviewEntity = ReviewEntity.builder().reviewContent(createReviewReqDTO.getReviewText())
                .rating(createReviewReqDTO.getReviewRating()).duo(duoRepository.findByDuoId(createReviewReqDTO.getDuoIdx()))
                .writerId(userRepository.selectByUserId(createReviewReqDTO.getUserIdx()))
                .revieweeId(duoEntity.getRequestedUserId())
                .build();

        duoEntity.setReviewWritten(true);

        int reviewIdx = reviewRepository.save(reviewEntity).getReviewId();


        //평점 반영
        userRepository.updateRating(duoEntity.getRequestedUserId().getUserIdx());

        return new CreateReviewResDTO(reviewIdx, "리뷰가 성공적으로 등록되었습니다.");

    }
}
