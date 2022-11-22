package com.cau.gamehaeduo.domain.note.entity;

import com.cau.gamehaeduo.domain.user.UserEntity;
import lombok.*;

import javax.persistence.*;

@Table(name="NoteParticipant")
@Entity(name="NoteParticipant")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NoteParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", name ="note_room_id")
    private Long noteRoomId;

    @Column(columnDefinition = "INT UNSIGNED",name="note_participant_id")
    private Long noteRoomName;

    @Column(columnDefinition = "INT",name="is_note_player")
    private int isNotePlayer;


    @ManyToOne
    @JoinColumn(name= "note_room_id")
    private NoteRoomEntity noteRoom ;


}
