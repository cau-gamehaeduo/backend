package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.duo.DuoEntity;
import com.cau.gamehaeduo.domain.note.entity.NoteMessageEntity;
import com.cau.gamehaeduo.domain.note.entity.NoteRoomEntity;
import com.cau.gamehaeduo.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DuoRepository extends JpaRepository<DuoEntity,Long> {

    int countByRequestedUserId(UserEntity user);
    int countByRequestUserId(UserEntity user);
}

