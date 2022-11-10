package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/player")
@RestController
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;


}
