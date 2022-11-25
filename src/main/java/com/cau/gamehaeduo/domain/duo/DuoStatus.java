package com.cau.gamehaeduo.domain.duo;

public enum DuoStatus {
    WAITING(1),
    PROCEEDING(2),
    COMPLETE(3),
    CANCEL(4),
    REFUSAL(5);
    private final int status;

    DuoStatus(int status) {
        this.status = status;
    }
}
