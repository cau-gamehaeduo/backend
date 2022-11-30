package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ReviewController {
    private final ReviewService reviewService;
}
