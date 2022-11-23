package com.cau.gamehaeduo.domain.note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomMessageResponseDTO {
    private Long messageId;
    private String noteMessage;
    private Long senderId;
}
