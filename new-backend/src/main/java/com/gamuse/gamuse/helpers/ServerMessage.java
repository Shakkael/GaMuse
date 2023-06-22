package com.gamuse.gamuse.helpers;

public class ServerMessage {
    public String message;
    public boolean success;

    public ServerMessage(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
