package com.ai.SpringAiDemo;

import java.util.List;

public class RoomStatus {
    private boolean success;
    private boolean opponentPresent;

    public RoomStatus(boolean success, boolean opponentPresent) {
        this.success = success;
        this.opponentPresent = opponentPresent;
    }

    public boolean isSuccess() { return success; }
    public boolean isOpponentPresent() { return opponentPresent; }
}


