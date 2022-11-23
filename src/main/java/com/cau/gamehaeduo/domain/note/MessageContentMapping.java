package com.cau.gamehaeduo.domain.note;

import java.sql.Timestamp;

public interface MessageContentMapping {
    Long getMessageId();
    String getNoteMessage();
    MessageContent getSenderId();
    MessageContent getReceiverId();
    Timestamp getSentAt();
    NoteRoomId getNoteRoom();

    interface MessageContent {
        int getUserIdx();
    }

    interface NoteRoomId {
        Long getNoteRoomId();
    }
}
