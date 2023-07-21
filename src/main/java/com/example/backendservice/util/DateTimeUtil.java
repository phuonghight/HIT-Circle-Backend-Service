package com.example.backendservice.util;

import com.example.backendservice.constant.CommonConstant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String toString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.PATTERN_DATE_TIME);
        return time.format(formatter);
    }

}
