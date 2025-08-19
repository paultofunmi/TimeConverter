package com.britishtime.converter.rules.impl;

import com.britishtime.converter.rules.TimeExpressionRule;
import java.time.LocalTime;

/** Handles special cases: midnight and noon. */
public class SpecialTimeRule extends TimeExpressionRule {

  @Override
  protected boolean canHandle(final LocalTime time) {
    return (time.getHour() == 0 && time.getMinute() == 0)
        || (time.getHour() == 12 && time.getMinute() == 0);
  }

  @Override
  protected String process(final LocalTime time) {
    if (time.getHour() == 0 && time.getMinute() == 0) {
      return "midnight";
    }
    return "noon";
  }
}
