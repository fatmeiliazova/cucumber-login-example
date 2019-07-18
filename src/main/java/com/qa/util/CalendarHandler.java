package com.qa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarHandler {
    private static Logger LOG = LoggerFactory.getLogger(CalendarHandler.class);

    public static String getCurrentDate(String pattern) {
        return getDate(pattern);
    }

    public static String getDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date d = c.getTime();
        return sdf.format(d);
    }

    public static String convertGivenDateToNewFormat(String dateToConvert, String originalFormat, String newFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(originalFormat);
        Date d = null;
        try {
            d = sdf.parse(dateToConvert);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat(newFormat);
        return sdf.format(d);
    }

    public static void main(String[] args) {
        String newDate = CalendarHandler.convertGivenDateToNewFormat("2019-02-24", "yyyy-MM-dd", "dd/MM/yyyy");
        System.out.println(newDate);
    }

}