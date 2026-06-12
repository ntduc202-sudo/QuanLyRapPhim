package com.nhom6.model;

public class CinemaRoom {
    private String roomId;
    private String roomName;

    public CinemaRoom(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    @Override
    public String toString() {
        return roomId + " - " + roomName;
    }
}