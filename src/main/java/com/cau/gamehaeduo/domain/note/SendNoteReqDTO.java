package com.cau.gamehaeduo.domain.note;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendNoteReqDTO {
    private String noteMessage;
    private String senderIdx;
}
