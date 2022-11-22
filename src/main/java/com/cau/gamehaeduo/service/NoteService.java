package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.note.SendFirstNoteReqDTO;
import com.cau.gamehaeduo.domain.note.SendNoteReqDTO;
import com.cau.gamehaeduo.domain.note.entity.NoteMessageEntity;
import com.cau.gamehaeduo.repository.NoteMessageRepository;
import com.cau.gamehaeduo.repository.NoteParticipantRepository;
import com.cau.gamehaeduo.repository.NoteRoomRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class NoteService {
    private final NoteRoomRepository noteRoomRepository;
    private final NoteParticipantRepository noteParticipantRepository;
    private final NoteMessageRepository noteMessageRepository;

    public NoteService(NoteRoomRepository noteRoomRepository, NoteParticipantRepository noteParticipantRepository, NoteMessageRepository noteMessageRepository) {
        this.noteRoomRepository = noteRoomRepository;
        this.noteParticipantRepository = noteParticipantRepository;
        this.noteMessageRepository = noteMessageRepository;
    }


    //쪽지 저장
    @Transactional
    public void sendNote(SendNoteReqDTO sendNoteReqDTO) throws BaseException {
        NoteMessageEntity noteMessageEntity = NoteMessageEntity.builder().
    }


    public void sendFirstNote(SendFirstNoteReqDTO sendFirstNoteReqDTO) throws BaseException{
        NoteMessageEntity noteMessage = NoteMessageEntity.builder()
                .noteMessage(sendFirstNoteReqDTO.getNoteMessage())
                .senderId(sendFirstNoteReqDTO.getSenderIdx())
                .receiverId(sendFirstNoteReqDTO.getReceiverIdx()).build();

        noteMessageRepository.save(noteMessage);


    }


    //기존 쪽지방에 메세지 저장
}
