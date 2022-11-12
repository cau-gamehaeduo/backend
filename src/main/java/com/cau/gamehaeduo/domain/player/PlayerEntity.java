package com.cau.gamehaeduo.domain.player;

import com.cau.gamehaeduo.domain.user.UserEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="Player")
public class PlayerEntity {
    @Id
    @Column(columnDefinition = "INT UNSIGNED", name = "player_id")
    private int id;

    @Column(length = 10)
    private String status;

    @Column(columnDefinition = "INT UNSIGNED")
    private int gender;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(columnDefinition = "TEXT")
    private String playStyle;

    @Column(length = 50)
    private String tier;

    @Column(columnDefinition = "INT UNSIGNED")
    private int price;

    @MapsId
    @OneToOne(mappedBy = "player")
    @JoinColumn(referencedColumnName = "player_id")
    private UserEntity user;
}
