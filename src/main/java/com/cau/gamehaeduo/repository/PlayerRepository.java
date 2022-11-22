package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.player.HomePartnerDTO;
import com.cau.gamehaeduo.domain.player.PlayerEntity;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;



@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    boolean existsById(int playerId);
    PlayerEntity findById(int playerId);

    //최근 등록한 플레이어 불러오기 (홈에서 세로 호출시 사용)
    @Query("select p from Player p where p.status= 'A' order by p.registeredAt desc")
    Page<PlayerEntity> findAllOrderBy_DateDesc(Pageable pageable);

    List<PlayerEntity> findTop10ByOrderByUserRatingDesc();

}
