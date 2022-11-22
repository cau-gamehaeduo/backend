package com.cau.gamehaeduo.repository;


import com.cau.gamehaeduo.domain.note.entity.NoteParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteParticipantRepository extends JpaRepository<NoteParticipantEntity, Long> {

}
