package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.review.ReviewEntity;
import com.cau.gamehaeduo.domain.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
    Page<ReviewEntity> findByRevieweeId(UserEntity user, Pageable pageable);
}
