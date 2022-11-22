package com.cau.gamehaeduo.domain.note.entity;


import lombok.*;

import javax.persistence.*;

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

    @Column(columnDefinition = "INT UNSIGNED", name ="note_room_id")
    private Long noteRoomId;


    @Column(columnDefinition = "INT UNSIGNED", name ="note_room_id")
    private String noteMessage;

    @Column(columnDefinition = "INT UNSIGNED",name="sender_id")
    private Long senderId;

    @ManyToOne
    @JoinColumn(name= "note_room_id")
    private NoteRoomEntity noteRoom ;

}
