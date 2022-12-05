package com.cau.gamehaeduo.repository;


import com.cau.gamehaeduo.domain.note.entity.NoteParticipantEntity;
import com.cau.gamehaeduo.domain.player.ParticipatingNoteRoomAndUserDTOInterface;
import com.cau.gamehaeduo.domain.user.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteParticipantRepository extends JpaRepository<NoteParticipantEntity, Long> {

    //참가해있는 채팅방과 참가자 목록 전부 불러오기
    @Query(value = "SELECT NoteParticipant.note_room_id as noteRoomIdx,\n" +
            "               NoteParticipant.note_participant_id as participatingUserIdx\n" +
            "        FROM NoteParticipant\n" +
            "        INNER JOIN (SELECT note_room_id FROM NoteParticipant WHERE note_participant_id = :userIdx) as CPcRI\n" +
            "            ON CPcRI.note_room_id = NoteParticipant.note_room_id", nativeQuery = true)
    List<ParticipatingNoteRoomAndUserDTOInterface> selectEveryParticipantInChatRoom(@Param("userIdx") Long userIdx);


    List<NoteParticipantEntity> findByNoteParticipantId(UserEntity user);
    List<NoteParticipantEntity> findByNoteRoom_NoteRoomId(Long roomId);

    @Query(value = "SELECT distinct p\n"+
                    "FROM NoteParticipant n \n"+
                    "INNER JOIN NoteParticipant p\n"+
                    "ON n.noteParticipantId.userIdx = :userIdx\n"+
                    "WHERE p.noteRoom = n.noteRoom AND p.noteParticipantId.userIdx <> :userIdx")
    List<NoteParticipantEntity> selectEveryOtherUserInUserChatRoom(int userIdx);

}
