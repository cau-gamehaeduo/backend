package com.cau.gamehaeduo.domain.note;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendFirstNoteReqDTO {
    private String noteMessage;
    private Long senderIdx;
    private Long receiverIdx;
}
