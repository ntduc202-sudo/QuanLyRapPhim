package com.nhom6.service;

import com.nhom6.model.SeatType;
import com.nhom6.repository.FileRepository;

import java.util.ArrayList;
import java.util.List;

public class SeatService {
    private FileRepository fileRepository = new FileRepository();

    public List<String> getSeatNumbersByType(SeatType seatType, String showTimeId) {
        List<String> seats = new ArrayList<>();
        int maxSeat = getMaxSeatByType(seatType);

        for (int i = 1; i <= maxSeat; i++) {
            String seatNumber = getSeatPrefix(seatType) + i;

            if (isSeatBooked(showTimeId, seatNumber)) {
                seats.add(seatNumber + " - DA DAT");
            } else {
                seats.add(seatNumber);
            }
        }

        return seats;
    }

    public boolean isSeatBooked(String showTimeId, String seatNumber) {
        return fileRepository.isSeatBooked(showTimeId, seatNumber);
    }

    public boolean hasAvailableSeat(String showTimeId) {
        for (SeatType seatType : SeatType.values()) {
            int maxSeat = getMaxSeatByType(seatType);

            for (int i = 1; i <= maxSeat; i++) {
                String seatNumber = getSeatPrefix(seatType) + i;

                if (!isSeatBooked(showTimeId, seatNumber)) {
                    return true;
                }
            }
        }

        return false;
    }

    private int getMaxSeatByType(SeatType seatType) {
        if (seatType == SeatType.NORMAL) {
            return 10;
        }

        if (seatType == SeatType.VIP) {
            return 5;
        }

        if (seatType == SeatType.COUPLE) {
            return 5;
        }

        return 0;
    }

    private String getSeatPrefix(SeatType seatType) {
        if (seatType == SeatType.NORMAL) {
            return "N";
        }

        if (seatType == SeatType.VIP) {
            return "V";
        }

        if (seatType == SeatType.COUPLE) {
            return "C";
        }

        return "";
    }
}