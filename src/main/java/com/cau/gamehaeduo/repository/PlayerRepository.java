package com.cau.gamehaeduo.repository;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {
    boolean existsById(int playerId);

    PlayerEntity findById(int playerId);

    //최근 등록한 플레이어 불러오기 (홈에서 세로 호출시 사용)
    @Query("select p from Player p where p.status= 'A' order by p.registeredAt desc")
    Page<PlayerEntity> findAllOrderBy_DateDesc(Pageable pageable);

    List<PlayerEntity> findTop10ByOrderByUserRatingDesc();

    Page<PlayerEntity> findByStatusEquals(String status, Pageable pageable);

}
