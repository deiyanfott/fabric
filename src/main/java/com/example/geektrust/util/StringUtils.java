package com.example.geektrust.util;

public class StringUtils {
    private StringUtils() {

    }

    public static String[] splitString(String value, String delimiter) {
        return value.split(delimiter);
    }

    public static int convertStringToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(value + " is not a number");
        }
    }
}
