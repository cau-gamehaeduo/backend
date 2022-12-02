package com.cau.gamehaeduo.domain.user;

import com.cau.gamehaeduo.domain.player.PlayerEntity;
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
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name="User")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userIdx;
    @Column
    private String nickname;
    @Column(name="profile_photo_url")
    private String profilePhotoUrl;
    @Column(name="is_player")
    private String isPlayer;
    @Column
    private float rating;
    @Column(name="created_at")
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
    @Column
    private String id;
    @Column
    private String password;

    @Column(name = "kakao_id")
    private long kakaoIdx;

    private int point;

    @OneToOne
    @PrimaryKeyJoinColumn
    private PlayerEntity player;

    public UserEntity(int userIdx, String nickname, String profilePhotoUrl, String isPlayer, float rating,
                      String createdAt,
                      String status, int top, int jungle, int mid, int ad, int supporter, long kakaoIdx) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.profilePhotoUrl = profilePhotoUrl;
        this.isPlayer = isPlayer;
        this.rating = rating;
        this.createdAt = createdAt;
        this.status = status;
        this.top = top;
        this.jungle = jungle;
        this.mid = mid;
        this.ad = ad;
        this.supporter = supporter;
        this.kakaoIdx = kakaoIdx;
    }

    public UserEntity(String nickname, String profilePhotoUrl, int top, int jungle, int mid, int ad, int supporter, long kakaoIdx) {
        this.nickname = nickname;
        this.profilePhotoUrl = profilePhotoUrl;
        this.top = top;
        this.jungle = jungle;
        this.mid = mid;
        this.ad = ad;
        this.supporter = supporter;
        this.kakaoIdx = kakaoIdx;
    }

    public UserEntity(String nickname, String profilePhotoUrl, int top, int jungle, int mid, int ad, int supporter, String id, String password) {
        this.nickname = nickname;
        this.profilePhotoUrl = profilePhotoUrl;
        this.top = top;
        this.jungle = jungle;
        this.mid = mid;
        this.ad = ad;
        this.supporter = supporter;
        this.id = id;
        this.password = password;
    }

//    public void requestAndReducePoint(int price){
//        this.point = point-price;
//    }
//
//    public void requestedAndGetPoint(int price){
//        this.point = point+price;
//    }

    public UserEntity(int userIdx, String nickname, String profilePhotoUrl, String isPlayer, float rating,
                      String createdAt,
                      String status, int top, int jungle, int mid, int ad, int supporter, long kakaoIdx, int point) {
        this.userIdx = userIdx;
        this.nickname = nickname;
        this.profilePhotoUrl = profilePhotoUrl;
        this.isPlayer = isPlayer;
        this.rating = rating;
        this.createdAt = createdAt;
        this.status = status;
        this.top = top;
        this.jungle = jungle;
        this.mid = mid;
        this.ad = ad;
        this.supporter = supporter;
        this.kakaoIdx = kakaoIdx;
        this.point = point;
    }
}
