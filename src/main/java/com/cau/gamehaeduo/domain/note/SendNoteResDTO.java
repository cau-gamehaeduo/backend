package com.cau.gamehaeduo.domain.note;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendNoteResDTO {
    private Boolean isCreated;
    private String resultMessage;
    private Long noteRoomIdx;
}
