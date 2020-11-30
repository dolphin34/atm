package com.vndirect.atm.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    public static boolean isNumericString(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    public static String amountToString(long amount) {
        return String.format("%,d", amount) + " VND  ";
    }

    public static String dateToString(LocalDateTime date) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("E hh:mm:ss a, dd-MMM-yyyy");
        return date.format(pattern);
    }
}
