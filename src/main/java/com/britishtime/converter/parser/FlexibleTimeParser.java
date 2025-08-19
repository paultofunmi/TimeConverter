package com.britishtime.converter.parser;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** Concrete strategy implementing flexible time parsing. Handles both H:mm and HH:mm formats. */
public class FlexibleTimeParser implements TimeParser {

  private static final DateTimeFormatter FLEXIBLE_FORMATTER =
      DateTimeFormatter.ofPattern("[H:mm][HH:mm]");

  @Override
  public LocalTime parse(final String timeString) {
    if (!isValidFormat(timeString)) {
      throw new DateTimeParseException("Invalid time format", timeString, 0);
    }
    return LocalTime.parse(timeString, FLEXIBLE_FORMATTER);
  }

  private boolean isValidFormat(final String timeString) {
    if (timeString == null) {
      throw new IllegalArgumentException("Time input cannot be null or empty");
    }
    return timeString.matches("^\\d{1,2}:\\d{2}$");
  }
}
