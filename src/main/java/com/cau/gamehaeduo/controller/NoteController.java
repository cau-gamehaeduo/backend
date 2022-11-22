package com.cau.gamehaeduo.controller;


import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.note.SendFirstNoteReqDTO;
import com.cau.gamehaeduo.domain.note.SendNoteReqDTO;
import com.cau.gamehaeduo.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

//    @GetMapping("/note")
//    public BaseResponse<> getMessages(@RequestParam MessageRoomRequestDTO requestDTO) {
//        try {
//
//        } catch (BaseException e) {
//            log.error(" API : api/note" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
//            return new BaseResponse<>(e.getStatus());
//        }
//
//    }

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
