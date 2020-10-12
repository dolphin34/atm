package utils;

import java.util.Date;

public class StringUtil {
    public static String formatNumericString(String str) {
        str = str.trim();
        str = str.replaceAll("\\s+", "");
        return str;
    }

    public static boolean isNumericString(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void printMessages(String... messages) {
        for (String str: messages) {
            System.out.println(str);
        }
    }

    public static String amountToString(long amount) {
        return String.format("%,d", amount) + " VND   ";
    }

    public static String dateToString(Date date) {
        return String.format("%tT", date) + "," + String.format("%tD", date);
    }
}
