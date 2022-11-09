package com.cau.gamehaeduo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
public class UserController {
    @RequestMapping("/test")
    public String testString(){
        try{
            return "testpage";
        }catch (Exception e){
            log.error(e);
            return "error";
        }
    }
}
