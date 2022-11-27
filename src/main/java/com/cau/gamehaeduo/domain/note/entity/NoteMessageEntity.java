package com.cau.gamehaeduo.domain.note.entity;


import com.cau.gamehaeduo.domain.user.UserEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Table(name="NoteMessage")
@Entity(name="NoteMessage")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NoteMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", name ="message_id")
    private Long messageId;

    @Column(columnDefinition = "INT UNSIGNED", name ="note_message")
    private String noteMessage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sender_id")
    private UserEntity senderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="receiver_id")
    private UserEntity receiverId;

    @Column(name="sent_at")
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name= "note_room_id")
    private NoteRoomEntity noteRoom ;

}
