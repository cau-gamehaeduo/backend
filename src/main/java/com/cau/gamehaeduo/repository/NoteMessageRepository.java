package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.note.entity.NoteMessageEntity;
import com.cau.gamehaeduo.domain.player.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteMessageRepository extends JpaRepository<NoteMessageEntity, Long> {

}
