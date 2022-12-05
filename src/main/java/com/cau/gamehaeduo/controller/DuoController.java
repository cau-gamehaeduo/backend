package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.duo.*;
import com.cau.gamehaeduo.domain.player.PlayerProfileResponseDTO;
import com.cau.gamehaeduo.service.DuoService;
import com.cau.gamehaeduo.service.JwtService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public BaseResponse<DuoRequestResDTO> requestDuo(@RequestBody DuoRequestDTO duoRequestDTO){

        try{
            //jwtService.validateAccessToken(duoRequestDTO.getUserIdx());
            return new BaseResponse<>(duoService.requestDuo(duoRequestDTO));
        }
        catch (BaseException e){
            log.error(" API : api/duo" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }

    }


    //듀오 신청 갯수 가져오기
    @GetMapping("/num")
    public BaseResponse<DuoNumResDTO> getDuoNum(@RequestParam("userIdx") int userIdx){
        try{
            jwtService.validateAccessToken(userIdx);
            return new BaseResponse<>(duoService.getDuoNum(userIdx));
        }
        catch (BaseException e){
            log.error(" API : api/duo/num" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

    //듀오 완료 하기
    @PostMapping("/finish")
    public BaseResponse<FinishDuoResDTO> finishDuo(@RequestBody FinishDuoRequestDTO finishDuoRequestDTO ){
        try{
            jwtService.validateAccessToken(finishDuoRequestDTO.getUserIdx());
            return new BaseResponse<>(duoService.finishDuo(finishDuoRequestDTO));
        }
        catch (BaseException e){
            log.error(" API : api/duo/num" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }


    //듀오 취소하기
    @PostMapping("/cancel")
    public BaseResponse<CancelDuoResDTO> cancelDuo(@RequestBody CancelDuoRequestDTO cancelDuoRequestDTO ){
        try{
            jwtService.validateAccessToken(cancelDuoRequestDTO.getUserIdx());
            return new BaseResponse<>(duoService.cancelDuo(cancelDuoRequestDTO));
        }
        catch (BaseException e){
            log.error(" API : api/duo/cancel" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

    //듀오 수락하기
    @PostMapping("/accept")
    public BaseResponse<AcceptDuoResDTO> acceptDuo(@RequestBody AcceptDuoRequestDTO acceptDuoRequestDTO ){
        try{
            jwtService.validateAccessToken(acceptDuoRequestDTO.getUserIdx());
            return new BaseResponse<>(duoService.acceptDuo(acceptDuoRequestDTO));
        }
        catch (BaseException e){
            log.error(" API : api/duo/accept" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/request")
    public BaseResponse<List<DuoInfoResponseDTO>> getRequestDuo(@RequestParam("userIdx") int userId) {
        try {
            jwtService.validateAccessToken(userId);
            return new BaseResponse<>(duoService.getRequestDuo(userId));
        } catch (BaseException e) {
            log.error(" API : api/duo/request" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/requested")
    public BaseResponse<List<DuoInfoResponseDTO>> getRequestrequestededDuo(@RequestParam("userIdx") int userId) {
        //      try {
            //jwtService.validateAccessToken(userId);
            return new BaseResponse<>(duoService.getRequestedDuo(userId));
//        } catch (BaseException e) {
//            log.error(" API : api/duo/requested" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
//            return new BaseResponse<>(e.getStatus());
//        }
    }
}
