package com.nhom6.service;

import com.nhom6.model.ShowTime;
import com.nhom6.repository.FileRepository;

import java.util.List;

public class ShowTimeService {
    private FileRepository fileRepository = new FileRepository();

    public List<ShowTime> getAllShowTimes() {
        return fileRepository.loadShowTimes();
    }

    public List<ShowTime> getShowTimesByMovieId(String movieId) {
        return fileRepository.loadShowTimesByMovieId(movieId);
    }

    public void addShowTime(ShowTime showTime) {
        List<ShowTime> showTimes = fileRepository.loadShowTimes();
        showTimes.add(showTime);
        fileRepository.saveAllShowTimes(showTimes);
    }

    public void updateShowTime(ShowTime oldShowTime, ShowTime newShowTime) {
        List<ShowTime> showTimes = fileRepository.loadShowTimes();

        for (int i = 0; i < showTimes.size(); i++) {
            if (showTimes.get(i).getShowTimeId().equalsIgnoreCase(oldShowTime.getShowTimeId())) {
                showTimes.set(i, newShowTime);
                break;
            }
        }

        fileRepository.saveAllShowTimes(showTimes);
    }

    public void deleteShowTime(ShowTime showTime) {
        List<ShowTime> showTimes = fileRepository.loadShowTimes();
        showTimes.removeIf(st -> st.getShowTimeId().equalsIgnoreCase(showTime.getShowTimeId()));
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

    public boolean isDuplicateShowTimeIdForUpdate(String showTimeId, ShowTime selectedShowTime) {
        for (ShowTime showTime : fileRepository.loadShowTimes()) {
            if (!showTime.getShowTimeId().equalsIgnoreCase(selectedShowTime.getShowTimeId())
                    && showTime.getShowTimeId().equalsIgnoreCase(showTimeId)) {
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
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }
    public boolean isRoomBusy(String roomId, String showDate, String startTime, String endTime, ShowTime currentShowTime) {
        for (ShowTime showTime : fileRepository.loadShowTimes()) {
            if (showTime.getShowTimeId().equalsIgnoreCase(currentShowTime.getShowTimeId())) {
                continue;
            }

            if (showTime.getRoom().equalsIgnoreCase(roomId)
                    && showTime.getShowDate().equalsIgnoreCase(showDate)
                    && isTimeOverlap(startTime, endTime, showTime.getShowTime(), showTime.getEndTime())) {
                return true;
            }
        }

        return false;
    }
}