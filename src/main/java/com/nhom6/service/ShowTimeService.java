package com.nhom6.service;

import com.nhom6.model.ShowTime;
import com.nhom6.repository.FileRepository;

import java.util.List;

public class ShowTimeService {
    private FileRepository fileRepository = new FileRepository();

    public List<ShowTime> getAllShowTimes() {
        return fileRepository.loadShowTimes();
    }

    public void addShowTime(ShowTime showTime) {
        List<ShowTime> showTimes = fileRepository.loadShowTimes();
        showTimes.add(showTime);
        fileRepository.saveAllShowTimes(showTimes);
    }

    public boolean isDuplicateShowTimeId(String showTimeId) {
        for (ShowTime showTime : fileRepository.loadShowTimes()) {
            if (showTime.getShowTimeId().equalsIgnoreCase(showTimeId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRoomBusy(String roomId, String showDate, String startTime, String endTime) {
        for (ShowTime showTime : fileRepository.loadShowTimes()) {
            if (showTime.getRoom().equalsIgnoreCase(roomId)
                    && showTime.getShowDate().equalsIgnoreCase(showDate)
                    && isTimeOverlap(startTime, endTime, showTime.getShowTime(), showTime.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    private boolean isTimeOverlap(String start1, String end1, String start2, String end2) {
        int s1 = toMinutes(start1);
        int e1 = toMinutes(end1);
        int s2 = toMinutes(start2);
        int e2 = toMinutes(end2);
        return s1 < e2 && s2 < e1;
    }

    private int toMinutes(String time) {
        String[] p = time.split(":");
        return Integer.parseInt(p[0]) * 60 + Integer.parseInt(p[1]);
    }
}