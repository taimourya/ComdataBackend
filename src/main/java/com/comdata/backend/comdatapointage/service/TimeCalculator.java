package com.comdata.backend.comdatapointage.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;

@Service
public class TimeCalculator {

    public double difference(LocalTime t1, LocalTime t2) {
        double hour = t2.getHour() - t1.getHour();
        double minutes = t2.getMinute() - t1.getMinute();
        double seconds = t2.getSecond() - t1.getSecond();



        return hour + (minutes / 60) + (seconds / 3600);
    }

}
