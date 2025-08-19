package com.britishtime.converter.util;

public class NumberToWordConverter {

    private static final String[] HOUR_NAMES = {
            "twelve", "one", "two", "three", "four", "five",
            "six", "seven", "eight", "nine", "ten", "eleven"
    };

    private static final String[] MINUTE_NAMES = {
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
            "seventeen", "eighteen", "nineteen", "twenty"
    };

    private static final String[] TENS_NAMES = {
            "", "", "twenty", "thirty", "forty", "fifty"
    };

    public String getHourName(int hour) {
        return HOUR_NAMES[hour % 12];
    }

    public String getMinuteName(int minute) {
        if (minute <= 20) {
            return MINUTE_NAMES[minute];
        } else {
            int tens = minute / 10;
            int ones = minute % 10;
            return ones == 0 ? TENS_NAMES[tens] : TENS_NAMES[tens] + " " + MINUTE_NAMES[ones];
        }
    }
}
