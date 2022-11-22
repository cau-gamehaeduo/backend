package com.cau.gamehaeduo.controller;


import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.note.SendFirstNoteReqDTO;
import com.cau.gamehaeduo.domain.note.SendNoteReqDTO;
import com.cau.gamehaeduo.service.NoteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Log4j2
@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/note/send")
    public BaseResponse<SendNoteReqDTO> sendNote(@RequestBody SendNoteReqDTO sendNoteReqDTO){
        try{


            return new BaseResponse<>(noteService.sendNote(sendNoteReqDTO));
        }catch(BaseException e){
            log.error(" API : api/note/send" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

    //처음 쪽지 보낼때 호출 API
    @PostMapping("/note/send/new")
    public BaseResponse<SendNoteReqDTO> sendFirstNote(@RequestBody SendFirstNoteReqDTO sendFirstNoteReqDTO){
        try{
            return new BaseResponse<>(noteService.sendFirstNote(sendFirstNoteReqDTO));
        }catch(BaseException e){
            log.error(" API : api/note/send/new" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }
}
