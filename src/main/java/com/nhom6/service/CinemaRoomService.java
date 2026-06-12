package com.nhom6.service;

import com.nhom6.model.CinemaRoom;
import java.util.ArrayList;
import java.util.List;

public class CinemaRoomService {
    public List<CinemaRoom> getAllRooms() {
        List<CinemaRoom> rooms = new ArrayList<>();
        rooms.add(new CinemaRoom("ROOM01", "Phong 1"));
        rooms.add(new CinemaRoom("ROOM02", "Phong 2"));
        rooms.add(new CinemaRoom("ROOM03", "Phong 3"));
        return rooms;
    }
}