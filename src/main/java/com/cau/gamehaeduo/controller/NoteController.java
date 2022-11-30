package com.cau.gamehaeduo.controller;


import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponse;
import com.cau.gamehaeduo.domain.note.MessageRoomsResponseDTO;
import com.cau.gamehaeduo.domain.note.RoomMessageResponseDTO;
import com.cau.gamehaeduo.domain.note.*;
import com.cau.gamehaeduo.domain.player.ParticipatingNoteRoomAndUserDTOInterface;
import com.cau.gamehaeduo.service.JwtService;
import com.cau.gamehaeduo.service.NoteService;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api")
public class NoteController {
    private final JwtService jwtService;
    private final NoteService noteService;

    public NoteController(JwtService jwtService, NoteService noteService) {
        this.jwtService = jwtService;
        this.noteService = noteService;
    }

    @PostMapping("/note/send")
    public BaseResponse<SendNoteResDTO> sendNote(@RequestBody SendNoteReqDTO sendNoteReqDTO){
        try{

            //기존 쪽지 한적 있는지 없는지 모르는 상황
            if(sendNoteReqDTO.getNoteRoomIdx()==null || sendNoteReqDTO.getNoteRoomIdx() == 0){
                //기존 쪽지방 있는지 화인

                boolean alreadyHaveRoom = false;

                //senderUser가 참가해있는 채팅방과 참가자 목록
                List<ParticipatingNoteRoomAndUserDTOInterface> userParticipatingRoomAndUserList;
                userParticipatingRoomAndUserList = noteService.getParticatingNoteRoomAndUserDTO(sendNoteReqDTO.getSenderIdx());


                for (ParticipatingNoteRoomAndUserDTOInterface eachUserInChatRoom : userParticipatingRoomAndUserList) {
                    log.info("채팅방 목록의 다른 유저 = " + eachUserInChatRoom.getParticipatingUserIdx() );
                    if(eachUserInChatRoom.getParticipatingUserIdx().equals(sendNoteReqDTO.getReceiverIdx())){
                        alreadyHaveRoom = true;
                        sendNoteReqDTO.setNoteRoomIdx(eachUserInChatRoom.getNoteRoomIdx());
                        break;
                    }
                }

                //이미 방이 있다면 그 방에 보내고 결과 전송
                if(alreadyHaveRoom) {
                    log.info("이미 방이 있음");
                    return new BaseResponse<>(noteService.sendNote(sendNoteReqDTO));
                }//방이 없다면 방을 생성하고 그 방에 결과 저송
                else{
                    log.info("방이 없어서 새로 생성하고 쪽지 보냄");
                     return new BaseResponse<>(noteService.sendFirstNote(sendNoteReqDTO));
                }

            }

            jwtService.validateAccessToken(sendNoteReqDTO.getSenderIdx().intValue());
            return new BaseResponse<>(noteService.sendNote(sendNoteReqDTO));
        }catch(BaseException e){
            log.error(" API : api/note/send" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }

    @GetMapping("/room")
    public BaseResponse<List<MessageRoomsResponseDTO>> getUserRooms(@RequestParam("userIdx") int id) {
        try {
            return new BaseResponse<>(noteService.getUserRooms(id));
        } catch (BaseException e) {
            log.error(" API : api/room" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }
    }
//
//    //처음 쪽지 보낼때 호출 API
//    @PostMapping("/note/send/new")
//    public BaseResponse<SendFirstNoteResDTO> sendFirstNote(@RequestBody SendFirstNoteReqDTO sendFirstNoteReqDTO){
//        try{
//            jwtService.validateAccessToken(sendFirstNoteReqDTO.getSenderIdx().intValue());
//            return new BaseResponse<>(noteService.sendFirstNote(sendFirstNoteReqDTO));
//        }catch(BaseException e){
//            log.error(" API : api/note/send/new" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
//            return new BaseResponse<>(e.getStatus());
//        }
//    }

    @GetMapping("/note/room")
    public BaseResponse<RoomMessageResponseDTO> getMessages(@RequestParam("roomId") Long roomId,
                                                            @RequestParam("userIdx") int userId,
                                                            @RequestParam("duoIdx") int duoId) {
        try {
            //jwtService.validateAccessToken(userId);
            return new BaseResponse<>(noteService.getRoomMessages(roomId, duoId, userId));
        } catch (BaseException e) {
            log.error(" API : api/note" + "\n Message : " + e.getMessage() + "\n Cause : " + e.getCause());
            return new BaseResponse<>(e.getStatus());
        }

    }
}
