package com.vndirect.atm.util;

import com.vndirect.atm.exception.InvalidInputException;

import java.util.Date;

public class StringUtil {
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

    public static void printMessages(String... messages) {
        for (String str : messages) {
            System.out.println(str);
        }
        System.out.println();
    }

    public static String amountToString(long amount) {
        return String.format("%,d", amount) + " VND   ";
    }

    public static String dateToString(Date date) {
        return String.format("%tT", date) + "," + String.format("%tD", date);
    }
}
