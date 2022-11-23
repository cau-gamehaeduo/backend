package com.cau.gamehaeduo.domain.note;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageContentDTO {
    private Long messageId;
    private int senderId;
    private int receiverId;
    private Timestamp sendAt;
    private String noteMessage;
}
