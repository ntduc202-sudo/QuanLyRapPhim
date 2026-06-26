package com.nhom6.controller;

import com.nhom6.model.ShowTime;
import com.nhom6.service.ShowTimeService;

import java.util.List;

public class ShowTimeController {
    private ShowTimeService showTimeService = new ShowTimeService();

    public List<ShowTime> getAllShowTimes() {
        return showTimeService.getAllShowTimes();
    }

}