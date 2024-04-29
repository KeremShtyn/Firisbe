package com.example.firisbe.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserUtil {

    private UserUtil(){}

    public static boolean isNullOrEmptyObject(Object obj) {
        return (obj == null || obj == "");
    }

    public static final String formatLocalDateTimeAsString(LocalDateTime localDateTime, String dateFormat){
        var formatter = DateTimeFormatter.ofPattern(dateFormat);
        return localDateTime.format(formatter);
    }

}
