package com.britishtime.converter.rules.impl;

import com.britishtime.converter.rules.TimeExpressionRule;
import com.britishtime.converter.util.NumberToWordConverter;

import java.time.LocalTime;

/**
 * Handles times from 1-30 minutes past the hour
 */
public class PastHourRule extends TimeExpressionRule {

    private final NumberToWordConverter numberConverter;

    public PastHourRule(NumberToWordConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    @Override
    protected boolean canHandle(LocalTime time) {
        return time.getMinute() > 0 && time.getMinute() <= 30;
    }

    @Override
    protected String process(LocalTime time) {
        int minute = time.getMinute();
        String hourName = numberConverter.getHourName(time.getHour());

        if (minute == 15) {
            return "quarter past " + hourName;
        } else if (minute == 30) {
            return "half past " + hourName;
        } else if (minute <= 20 || minute == 25) {
            return numberConverter.getMinuteName(minute) + " past " + hourName;
        } else {
            // For irregular minutes like 32, use "hour minute" format
            return hourName + " " + numberConverter.getMinuteName(minute);
        }
    }
}
