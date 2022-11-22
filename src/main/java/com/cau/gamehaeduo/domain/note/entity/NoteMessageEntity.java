package com.cau.gamehaeduo.domain.note.entity;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name="NoteMessage")
@Entity(name="NoteMessage")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
public class NoteMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", name ="message_id")
    private Long messageId;

//    @Column(columnDefinition = "INT UNSIGNED", name ="note_room_id")
//    private Long noteRoomId;


    @Column(columnDefinition = "INT UNSIGNED", name ="note_message")
    private String noteMessage;

    @Column(columnDefinition = "INT UNSIGNED",name="sender_id")
    private Long senderId;

    @Column(columnDefinition = "INT UNSIGNED",name="receiver_id")
    private Long receiverId;

    @Column(name="sent_at")
    private Timestamp sentAt;

    @ManyToOne
    @JoinColumn(name= "note_room_id")
    private NoteRoomEntity noteRoom ;

}
