package com.vndirect.atm.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    public static boolean isNumericString(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    public static String amountToString(long amount) {
        return String.format("%,d", amount) + " VND  ";
    }

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("E hh:mm:ss a, dd-MMM-yyyy");
        return dateFormat.format(date);
    }
}
