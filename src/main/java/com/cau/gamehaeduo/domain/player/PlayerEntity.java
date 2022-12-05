package com.cau.gamehaeduo.domain.player;

import com.cau.gamehaeduo.domain.user.UserEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Table(name="Player")
@Entity(name="Player")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
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

    @Column(columnDefinition = "TEXT", name="play_style")
    private String playStyle;

    @Column(length = 50)
    private String tier;

    @Column(columnDefinition = "INT UNSIGNED")
    private int price;

    @OneToOne(mappedBy = "player")
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(name="registered_at")
    private LocalDateTime registeredAt;
}
