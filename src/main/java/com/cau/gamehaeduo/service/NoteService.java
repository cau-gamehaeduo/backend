package com.cau.gamehaeduo.service;

import com.cau.gamehaeduo.domain.base.BaseException;
import com.cau.gamehaeduo.domain.base.BaseResponseStatus;
import com.cau.gamehaeduo.domain.note.MessageContentDTO;
import com.cau.gamehaeduo.domain.note.MessageContentMapping;
import com.cau.gamehaeduo.domain.note.MessageRoomsResponseDTO;
import com.cau.gamehaeduo.domain.note.RoomMessageResponseDTO;
import com.cau.gamehaeduo.domain.note.SendFirstNoteReqDTO;
import com.cau.gamehaeduo.domain.note.SendFirstNoteResDTO;
import com.cau.gamehaeduo.domain.note.SendNoteReqDTO;
import com.cau.gamehaeduo.domain.note.SendNoteResDTO;
import com.cau.gamehaeduo.domain.note.entity.NoteMessageEntity;
import com.cau.gamehaeduo.domain.note.entity.NoteParticipantEntity;
import com.cau.gamehaeduo.domain.note.entity.NoteRoomEntity;
import com.cau.gamehaeduo.domain.user.UserEntity;
import com.cau.gamehaeduo.repository.NoteMessageRepository;
import com.cau.gamehaeduo.repository.NoteParticipantRepository;
import com.cau.gamehaeduo.repository.NoteRoomRepository;
import com.cau.gamehaeduo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class NoteService {
    private final NoteRoomRepository noteRoomRepository;
    private final NoteParticipantRepository noteParticipantRepository;
    private final NoteMessageRepository noteMessageRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRoomRepository noteRoomRepository, NoteParticipantRepository noteParticipantRepository, NoteMessageRepository noteMessageRepository, UserRepository userRepository) {
        this.noteRoomRepository = noteRoomRepository;
        this.noteParticipantRepository = noteParticipantRepository;
        this.noteMessageRepository = noteMessageRepository;
        this.userRepository = userRepository;
    }


    public RoomMessageResponseDTO getRoomMessages(Long roomId) throws BaseException {
        NoteRoomEntity noteRoom = noteRoomRepository.findById(roomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_NOTE_ROOM));
        List<MessageContentMapping> noteMessages = noteMessageRepository.findByNoteRoom(noteRoom);
        List<MessageContentDTO> messageContents = new ArrayList<>();
        for(MessageContentMapping messageContent: noteMessages) {
            messageContents.add(
                    MessageContentDTO.builder()
                            .messageId(messageContent.getMessageId())
                            .senderId(messageContent.getSenderId().getUserIdx())
                            .receiverId(messageContent.getReceiverId().getUserIdx())
                            .sendAt(messageContent.getSentAt())
                            .noteMessage(messageContent.getNoteMessage())
                            .build()
            );
        }
        return new RoomMessageResponseDTO(messageContents);
    }

    //이미 쪽지 한적 있는 상대방에게 쪽지 전송
    public SendNoteResDTO sendNote(SendNoteReqDTO sendNoteReqDTO) throws BaseException{

        //UserEntity 가져오기
        UserEntity sendUser = getUserEntity(sendNoteReqDTO.getSenderIdx().intValue());
        UserEntity receiveUser = getUserEntity(sendNoteReqDTO.getReceiverIdx().intValue());

        NoteRoomEntity noteRoom = noteRoomRepository.getReferenceById(sendNoteReqDTO.getNoteRoomIdx());
        //쪽지 저장
        NoteMessageEntity noteMessage = NoteMessageEntity.builder()
                .noteMessage(sendNoteReqDTO.getNoteMessage())
                .senderId(sendUser)
                .receiverId(receiveUser)
                .noteRoom(noteRoomRepository.getReferenceById(sendNoteReqDTO.getNoteRoomIdx()))
                .build();

        noteMessageRepository.save(noteMessage);


        //쪽지 참가자 저장   쪽지 신청한 사람은 isNotePlayer 0 쪽지 받는 플레이너는 isNotePlayer 1
        NoteParticipantEntity noteSenderParticipantEntity = NoteParticipantEntity.builder()
                .isNotePlayer(0)
                .noteRoom(
                        noteRoomRepository.getReferenceById(noteRoom.getNoteRoomId())
                )
                .noteParticipantId(
                        sendUser
                )
                .build();

        NoteParticipantEntity noteReceiverParticipantEntity = NoteParticipantEntity.builder()
                .isNotePlayer(1)
                .noteRoom(
                        noteRoomRepository.getReferenceById(noteRoom.getNoteRoomId())
                )
                .noteParticipantId(
                        receiveUser
                )
                .build();

        noteParticipantRepository.save(noteSenderParticipantEntity);
        noteParticipantRepository.save(noteReceiverParticipantEntity);


        return new SendNoteResDTO(true,"상대방에게  쪽지가 전송되었습니다.", noteRoom.getNoteRoomId());
    }


    //쪽지 처음 전송
    //새 채팅방 생성후 그 채팅방에 저장
    public SendFirstNoteResDTO sendFirstNote(SendFirstNoteReqDTO sendFirstNoteReqDTO) throws BaseException{
        NoteRoomEntity noteRoom = NoteRoomEntity.builder().build();

        //새 쪽지방 생성
        Long noteRoomIdx = noteRoomRepository.save(noteRoom).getNoteRoomId();


        //UserEntity 가져오기
        UserEntity sendUser = getUserEntity(sendFirstNoteReqDTO.getSenderIdx().intValue());
        UserEntity receiveUser = getUserEntity(sendFirstNoteReqDTO.getReceiverIdx().intValue());

        //쪽지 저장
        NoteMessageEntity noteMessage = NoteMessageEntity.builder()
                .noteMessage(sendFirstNoteReqDTO.getNoteMessage())
                .senderId(sendUser)
                .receiverId(receiveUser)
                .noteRoom(noteRoomRepository.getReferenceById(noteRoomIdx))
                .build();

        noteMessageRepository.save(noteMessage);


        //쪽지 참가자 저장   쪽지 신청한 사람은 isNotePlayer 0 쪽지 받는 플레이너는 isNotePlayer 1
        NoteParticipantEntity noteSenderParticipantEntity = NoteParticipantEntity.builder()
                .isNotePlayer(0)
                .noteRoom(
                        noteRoomRepository.getReferenceById(noteRoomIdx)
                )
                .noteParticipantId(
                        sendUser
                )
                .build();

        NoteParticipantEntity noteReceiverParticipantEntity = NoteParticipantEntity.builder()
                .isNotePlayer(1)
                .noteRoom(
                        noteRoomRepository.getReferenceById(noteRoomIdx)
                )
                .noteParticipantId(
                        receiveUser
                )
                .build();

        noteParticipantRepository.save(noteSenderParticipantEntity);
        noteParticipantRepository.save(noteReceiverParticipantEntity);


        return new SendFirstNoteResDTO(true,"상대방에게 첫 쪽지가 전송되었습니다.", noteRoomIdx);

    }

    private UserEntity getUserEntity(int userIdx) {
        List<UserEntity> userList = userRepository.selectByUserId(userIdx);
        UserEntity user = userList.get(0);
        return user;
    }

    public List<MessageRoomsResponseDTO> getUserRooms(int userIdx) throws BaseException {
        UserEntity user = userRepository.selectByUserId(userIdx).get(0);
        List<Long> rooms = noteParticipantRepository.findByNoteParticipantId(user).stream()
                .map(NoteParticipantEntity::getNoteRoom)
                .map(NoteRoomEntity::getNoteRoomId)
                .distinct()
                .collect(Collectors.toList());
        List<MessageRoomsResponseDTO> responseDTOList = new ArrayList<>();
        for(Long roomId: rooms) {
            NoteRoomEntity noteRoom = noteRoomRepository.findById(roomId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_NOTE_ROOM));
            NoteMessageEntity message = noteMessageRepository.findFirst1ByNoteRoomOrderBySentAtDesc(noteRoom);
            UserEntity otherUser = noteParticipantRepository.findByNoteRoom(noteRoom).stream()
                    .map(NoteParticipantEntity::getNoteParticipantId)
                    .filter(noteParticipantId -> noteParticipantId.getUserIdx() != userIdx)
                    .findFirst()
                    .orElse(null);
            MessageRoomsResponseDTO dto = MessageRoomsResponseDTO.builder()
                    .currentMessage(message.getNoteMessage())
                    .currentMessageTime(message.getSentAt())
                    .duoId(Objects.requireNonNull(otherUser).getUserIdx())
                    .duoName(otherUser.getNickname())
                    .profileImageUrl(otherUser.getProfilePhotoUrl())
                    .roomId(noteRoom.getNoteRoomId())
                    .build();
            responseDTOList.add(dto);
        }
        return responseDTOList;
    }
}
