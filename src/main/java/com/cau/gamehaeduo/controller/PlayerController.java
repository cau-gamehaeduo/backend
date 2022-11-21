package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.player.PlayerListDTO;
import com.cau.gamehaeduo.domain.player.PlayerProfileResponseDTO;
import com.cau.gamehaeduo.domain.player.PlayerRequestDTO;
import com.cau.gamehaeduo.domain.player.PlayerResponseDTO;
import com.cau.gamehaeduo.domain.player.ProfileResponseDTO;
import com.cau.gamehaeduo.service.JwtService;
import com.cau.gamehaeduo.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RequestMapping("/api/player")
@RestController
@RequiredArgsConstructor
@Log4j2
public class PlayerController {
    private final PlayerService playerService;
    private final JwtService jwtService;

    //Player 등록
    @PostMapping
    public BaseResponse<PlayerResponseDTO> registerPlayer(@RequestPart MultipartFile mFile, @Valid @RequestPart PlayerRequestDTO playerDto) throws BaseException {
        jwtService.validateAccessToken(playerDto.getUserIdx());
        PlayerResponseDTO result = playerService.registerPlayer(mFile, playerDto);
        return new BaseResponse<>(result);
    }

    //player 프로필 가져오기
    @GetMapping("/profile")
    public BaseResponse<ProfileResponseDTO> getPlayerProfile(@RequestParam("id") int id)
            throws BaseException {
        jwtService.validateAccessToken(id);
        ProfileResponseDTO result = playerService.getPlayerProfile(id);
        return new BaseResponse<>(result);
    }

    @GetMapping("/profile")
    public BaseResponse<PlayerProfileResponseDTO> getOtherPlayerProfile(@RequestParam("userIdx") int userIdx,
                                                                        @RequestParam("otherIdx") int otherIdx) throws BaseException{
        jwtService.validateAccessToken(userIdx);
        PlayerProfileResponseDTO result = playerService.getOtherPlayerProfile(otherIdx);
        return new BaseResponse<>(result);
    }

    //세로로 pageable 플레이어 조회 (현재 정렬기준 : 최신 등록순)
    @GetMapping("/profiles/column")
    public BaseResponse<PlayerListDTO> getHomeColumnProfiles(@RequestParam int userIdx, Pageable pageable){
        try{
            jwtService.validateAccessToken(userIdx);
            return new BaseResponse<>(playerService.getRecentRegisteredPlayers(pageable));
        }catch (BaseException exception){
            log.error(" API : GET api/player/profiles/column" +"\n Message : " + exception.getMessage() +"\n Cause : " + exception.getCause());
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @GetMapping("/profiles/row")
    public BaseResponse<PlayerListDTO> getHomeRowProfiles(@RequestParam int userIdx){
        try{
            jwtService.validateAccessToken(userIdx);
            return new BaseResponse<>(playerService.getHighRatingPlayers());
        }catch (BaseException exception){
            log.error(" API : GET api/player/profiles/row" +"\n Message : " + exception.getMessage() +"\n Cause : " + exception.getCause());
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
