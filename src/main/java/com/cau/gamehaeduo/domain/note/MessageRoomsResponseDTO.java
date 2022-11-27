package com.cau.gamehaeduo.domain.note;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRoomsResponseDTO {
    private Long roomId;
    private int duoId;
    private String duoName;
    private String profileImageUrl;
    private String currentMessage;
    private LocalDateTime currentMessageTime;
}
