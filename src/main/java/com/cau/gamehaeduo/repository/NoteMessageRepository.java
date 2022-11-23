package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.note.MessageContentMapping;
import com.cau.gamehaeduo.domain.note.entity.NoteMessageEntity;
import com.cau.gamehaeduo.domain.note.entity.NoteRoomEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteMessageRepository extends JpaRepository<NoteMessageEntity, Long> {
    List<MessageContentMapping> findByNoteRoom(NoteRoomEntity noteRoom);
}
