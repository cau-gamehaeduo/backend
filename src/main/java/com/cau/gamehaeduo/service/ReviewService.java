package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
}
