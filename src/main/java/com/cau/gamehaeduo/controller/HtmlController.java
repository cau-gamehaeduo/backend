package com.cau.gamehaeduo.controller;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.user.CheckNicknameResDTO;
import com.cau.gamehaeduo.domain.user.CreateUserReqDTO;
import com.cau.gamehaeduo.domain.user.CreateUserResDTO;
import com.cau.gamehaeduo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Pattern;

import static com.cau.gamehaeduo.domain.base.BaseResponseStatus.SIGNUP_EMPTY_USER_NICKNAME;
import static com.cau.gamehaeduo.domain.base.BaseResponseStatus.SIGNUP_INVALID_USER_NICKNAME;

@Log4j2
@Controller
public class HtmlController {

    @RequestMapping("/privacy")
    public String privacy(){
        try{
            return "privacy";
        }catch (Exception e){
            log.error(e);
            return "error";
        }
    }

    @RequestMapping("/policy")
    public String policy(){
        try{
            return "policy";
        }catch (Exception e){
            log.error(e);
            return "error";
        }
    }

}
