package com.cau.gamehaeduo.domain.review;

import com.cau.gamehaeduo.domain.duo.DuoEntity;
import com.cau.gamehaeduo.domain.user.UserEntity;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Table(name = "Review")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewee_id")
    private UserEntity revieweeId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "writer_id")
    private UserEntity writerId;

    @Column(name = "rating")
    private float rating;

    @Column(name = "review_time")
    private LocalDateTime reviewTime;

    @Column(name = "review_content")
    private String reviewContent;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "duo_id")
    private DuoEntity duo;

}
