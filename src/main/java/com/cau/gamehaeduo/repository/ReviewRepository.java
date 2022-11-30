package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {
}
