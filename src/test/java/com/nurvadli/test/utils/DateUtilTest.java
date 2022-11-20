package com.nurvadli.test.utils;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class DateUtilTest {

    @Test
    public void testConvertStringToLocalDate() {
        String inputDate = "2022-11-20";
        LocalDate localDate = DateUtil.toLocalDate(inputDate);
        assertEquals(2022, localDate.getYear());
        assertEquals(11, localDate.getMonthValue());
        assertEquals(20, localDate.getDayOfMonth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertStringToLocalDateFailed() {
        String inputDate = "11-2022-10";
        DateUtil.toLocalDate(inputDate);
    }
}
