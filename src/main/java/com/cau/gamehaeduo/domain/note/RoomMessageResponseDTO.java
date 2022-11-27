package com.cau.gamehaeduo.domain.note;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomMessageResponseDTO {
    private RoomPlayerProfileDTO duoProfile;
    private List<MessageContentDTO> message;
}
