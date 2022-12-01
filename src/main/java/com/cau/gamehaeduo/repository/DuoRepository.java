package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.duo.DuoEntity;
import com.cau.gamehaeduo.domain.user.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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


    //둘 사이의 듀오 존재 하는 것들 가져옴
    //가장 최신에 만들어진것 가져오기
    @Query(value = "select * from Duo where ((Duo.requested_player_id = :requestedUserId AND Duo.request_user_id = :requestUserId) or (Duo.requested_player_id = :requestUserId AND Duo.request_user_id = :requestedUserId)) order by duo_id desc limit 1 ", nativeQuery = true)
    Optional<DuoEntity> selectExistRecentDuo(@Param("requestedUserId") int requestedUserId, @Param("requestUserId") int requestUserId);

}

