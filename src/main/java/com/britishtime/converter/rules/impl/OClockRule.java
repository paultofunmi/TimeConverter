package com.britishtime.converter.rules.impl;

import com.britishtime.converter.rules.TimeExpressionRule;
import com.britishtime.converter.util.NumberToWordConverter;
import java.time.LocalTime;

/** Handles exact hour times (X o'clock). */
public class OClockRule extends TimeExpressionRule {

  private final NumberToWordConverter numberConverter;

  public OClockRule(final NumberToWordConverter numberConverter) {
    this.numberConverter = numberConverter;
  }

  @Override
  protected boolean canHandle(final LocalTime time) {
    return time.getMinute() == 0;
  }

  @Override
  protected String process(final LocalTime time) {
    return numberConverter.getHourName(time.getHour()) + " o'clock";
  }
}
