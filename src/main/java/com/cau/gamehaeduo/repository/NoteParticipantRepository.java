package com.cau.gamehaeduo.repository;


import com.cau.gamehaeduo.domain.note.entity.NoteParticipantEntity;
import com.cau.gamehaeduo.domain.user.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteParticipantRepository extends JpaRepository<NoteParticipantEntity, Long> {
    List<NoteParticipantEntity> findByNoteParticipantId(UserEntity user);
}
