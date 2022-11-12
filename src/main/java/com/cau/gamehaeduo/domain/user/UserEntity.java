package com.cau.gamehaeduo.domain.user;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userIdx;
    @Column
    private String nickname;
    @Column
    private String profilePhotoUrl;
    @Column
    private String isPlayer;
    @Column
    private float rating;
    @Column
    private String createdAt;
    @Column
    private String status;
    @Column
    private int top;
    @Column
    private int jungle;
    @Column
    private int mid;
    @Column
    private int ad;
    @Column
    private int supporter;

    @Column(name = "kakao_id")
    private Long kakaoIdx;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PlayerEntity player;

    public UserEntity(String nickname, String profilePhotoUrl, int top, int jungle, int mid, int ad, int supporter) {
        this.nickname = nickname;
        this.profilePhotoUrl = profilePhotoUrl;
        this.top = top;
        this.jungle = jungle;
        this.mid = mid;
        this.ad = ad;
        this.supporter = supporter;
    }
}
