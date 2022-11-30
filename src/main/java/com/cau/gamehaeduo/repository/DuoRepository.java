package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.duo.DuoEntity;
import com.cau.gamehaeduo.domain.user.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DuoRepository extends JpaRepository<DuoEntity,Long> {

    int countByRequestedUserId(UserEntity user);
    int countByRequestUserId(UserEntity user);
    List<DuoEntity> findByRequestUserId(UserEntity user);
    List<DuoEntity> findByRequestedUserId(UserEntity user);
    DuoEntity findByDuoId(int duoId);
    DuoEntity findFirst1ByRequestedUserIdAndRequestUserIdOrderByRequestTimeDesc(UserEntity requestedUser, UserEntity requestUser);

    @Transactional
    @Modifying
    @Query("update Duo d set d.status = :status  where d.duoId = :duoIdx")
    int updateDuoStatus(@Param("status") String status, @Param("duoIdx") int duoIdx);
}

