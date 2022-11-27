package com.cau.gamehaeduo.domain.note;

import java.time.LocalDateTime;

public interface MessageContentMapping {
    Long getMessageId();
    String getNoteMessage();
    MessageContent getSenderId();
    MessageContent getReceiverId();
    LocalDateTime getSentAt();
    NoteRoomId getNoteRoom();

    interface MessageContent {
        int getUserIdx();
    }

    interface NoteRoomId {
        Long getNoteRoomId();
    }
}
