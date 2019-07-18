package com.qa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Timer {
    private static Logger LOG = LoggerFactory.getLogger(Timer.class);

    public static void waitUntil(String scheduledTime, String format) {
        try {
            Date currentTime;
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date scheduled = sdf.parse(scheduledTime);
            currentTime = new Date();
            while (currentTime.compareTo(scheduled) <= 0) {
                currentTime = new Date();
                System.out.println("Waiting for scheduled Time to be reached...");
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
