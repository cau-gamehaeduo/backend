package com.cau.gamehaeduo.domain.note;

import com.cau.gamehaeduo.domain.duo.DuoEntity;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomMessageResponseDTO {
    private RoomPlayerProfileDTO duoProfile;
    private String duoStatus;
    private List<MessageContentDTO> message;
    private boolean isRequestUser;
    private Integer duoIdx;

    public RoomMessageResponseDTO(RoomPlayerProfileDTO duoProfile, List<MessageContentDTO> message, DuoEntity duoEntity,
                                  int userId) {
        this.duoProfile = duoProfile;
        this.message = message;
        if (duoEntity == null) {
            this.duoStatus = null;
            this.isRequestUser = false;
            this.duoIdx = null;
        } else {
            this.duoIdx= duoEntity.getDuoId();
            this.duoStatus = duoEntity.getStatus();
            this.isRequestUser = duoEntity.getRequestUserId().getUserIdx() == userId;
        }
    }
}
