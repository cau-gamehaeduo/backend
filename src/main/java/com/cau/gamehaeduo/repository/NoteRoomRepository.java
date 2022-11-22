package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.note.entity.NoteRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRoomRepository extends JpaRepository<NoteRoomEntity, Long> {

}
