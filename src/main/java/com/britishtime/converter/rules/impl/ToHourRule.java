package com.britishtime.converter.rules.impl;

import com.britishtime.converter.rules.TimeExpressionRule;
import com.britishtime.converter.util.NumberToWordConverter;

import java.time.LocalTime;

/**
 * Handles times from 31-59 minutes (expressed as "to" the next hour)
 */
public class ToHourRule extends TimeExpressionRule {

    private final NumberToWordConverter numberConverter;

    public ToHourRule(NumberToWordConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    @Override
    protected boolean canHandle(LocalTime time) {
        return time.getMinute() > 30;
    }

    @Override
    protected String process(LocalTime time) {
        int minutesToNextHour = 60 - time.getMinute();
        String nextHourName = numberConverter.getHourName((time.getHour() + 1) % 24);

        if (minutesToNextHour == 15) {
            return "quarter to " + nextHourName;
        } else {
            return numberConverter.getMinuteName(minutesToNextHour) + " to " + nextHourName;
        }
    }
}
