package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.player.PlayerProfileResponseDTO;
import com.cau.gamehaeduo.service.DuoService;
import com.cau.gamehaeduo.service.JwtService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/duo")
@RestController
@RequiredArgsConstructor
@Log4j2
public class DuoController {
    private final DuoService duoService;
    private final JwtService jwtService;

    @GetMapping("/search")
    public BaseResponse<List<PlayerProfileResponseDTO>> getSearchDuo(@RequestParam("userIdx") int userIdx, Pageable pageable) {
        try {
            jwtService.validateAccessToken(userIdx);
            return new BaseResponse<>(duoService.getAllPlayer(userIdx, pageable));
        } catch (BaseException exception) {
            log.error(" API : GET api/duo/search" +"\n Message : " + exception.getMessage() +"\n Cause : " + exception.getCause());
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
