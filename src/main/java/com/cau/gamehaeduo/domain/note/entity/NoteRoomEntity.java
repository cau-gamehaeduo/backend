package com.cau.gamehaeduo.domain.note.entity;


import com.cau.gamehaeduo.domain.player.PlayerEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name="NoteRoom")
@Entity(name="NoteRoom")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NoteRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED", name ="note_room_id")
    private Long noteRoomId;

    @Column(columnDefinition = "INT UNSIGNED",name="note_room_name")
    private Long noteRoomName;

}
