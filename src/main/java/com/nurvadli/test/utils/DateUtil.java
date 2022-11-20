package com.nurvadli.test.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    private static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static LocalDate toLocalDate(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD);
        LocalDate date = null;
        try {
            date = LocalDate.parse(inputDate, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid string date");
        }

        return date;

    }
}
