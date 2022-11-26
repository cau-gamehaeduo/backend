package com.cau.gamehaeduo.domain.duo;


import com.cau.gamehaeduo.domain.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@DynamicInsert
@Table(name="Duo")
@Entity(name = "Duo")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DuoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", name = "duo_id")
    private int duoId;

    @ManyToOne
    @JoinColumn(name="request_user_id")
    private UserEntity requestUserId;

    @ManyToOne
    @JoinColumn(name="requested_player_id")
    private UserEntity requestedUserId;

    @Column(length = 40)
    private String status;

    @Column(columnDefinition = "INT UNSIGNED")
    private int price;

    @Column(name="request_time")
    private Timestamp requestTime;

    @Builder
    public DuoEntity(UserEntity requestUserId, UserEntity requestedUserId, String status, int price){
        this.requestUserId = requestUserId;
        this.requestedUserId = requestedUserId;
        this.status = status;
        this.price = price;
    }

}
