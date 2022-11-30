package com.cau.gamehaeduo.domain.note.entity;


import com.cau.gamehaeduo.domain.player.PlayerEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
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


}
