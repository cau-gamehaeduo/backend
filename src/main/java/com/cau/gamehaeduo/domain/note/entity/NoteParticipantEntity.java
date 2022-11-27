package com.cau.gamehaeduo.domain.note.entity;

import javax.persistence.*;

import com.cau.gamehaeduo.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Table(name="NoteParticipant")
@Entity(name="NoteParticipant")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NoteParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", name ="note_participant_column_id")
    private Long noteParticipantColumnId;


    @ManyToOne
    @JoinColumn(name ="note_participant_id")
    private UserEntity noteParticipantId;



    @ManyToOne
    @JoinColumn (name ="note_room_id")
    private NoteRoomEntity noteRoom ;

    @Column(columnDefinition = "INT",name="is_note_player")
    private int isNotePlayer;

}
