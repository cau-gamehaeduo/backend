package com.cau.gamehaeduo.domain.note.entity;


import com.cau.gamehaeduo.domain.user.UserEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private Timestamp sentAt;

    @ManyToOne
    @JoinColumn(name= "note_room_id")
    private NoteRoomEntity noteRoom ;

}
