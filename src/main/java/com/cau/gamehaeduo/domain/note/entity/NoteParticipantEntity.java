package com.cau.gamehaeduo.domain.note.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @PrimaryKeyJoinColumn
    private NoteRoomEntity noteRoom ;


}
