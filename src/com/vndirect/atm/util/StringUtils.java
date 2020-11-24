package com.vndirect.atm.util;

import com.vndirect.atm.exception.InvalidInputException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String formatNumericString(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", "");
        return str;
    }

    public static void isNumericString(String str) throws InvalidInputException {
        try {
            for (int i = 0; i < str.length(); i++) {
                Integer.parseInt(String.valueOf(str.charAt(i)));
            }
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }
    }

    public static String amountToString(long amount) {
        return String.format("%,d", amount) + " VND  ";
    }

    public static String dateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("E hh:mm:ss a, dd-MMM-yyyy");
        return dateFormat.format(date);
    }
}
