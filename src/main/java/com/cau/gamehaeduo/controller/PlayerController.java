package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.player.PlayerRequestDTO;
import com.cau.gamehaeduo.domain.player.PlayerResponseDTO;
import com.cau.gamehaeduo.domain.player.ProfileResponseDTO;
import com.cau.gamehaeduo.service.JwtService;
import com.cau.gamehaeduo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/player")
@RestController
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;
    private final JwtService jwtService;

    @PostMapping
    public BaseResponse<PlayerResponseDTO> registerPlayer(@RequestBody PlayerRequestDTO playerDto) throws BaseException {
        jwtService.validateAccessToken(playerDto.getUserIdx());
        PlayerResponseDTO result = playerService.registerPlayer(playerDto);
        return new BaseResponse<>(result);
    }

    @GetMapping("/profile")
    public BaseResponse<ProfileResponseDTO> getPlayerProfile(@RequestParam("id") int id)
            throws BaseException {
        jwtService.validateAccessToken(id);
        ProfileResponseDTO result = playerService.getPlayerProfile(id);
        return new BaseResponse<>(result);
    }
}
