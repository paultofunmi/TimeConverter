package com.britishtime.converter.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FlexibleTimeParserTest {

  private FlexibleTimeParser parser;

  @BeforeEach
  void setUp() {
    parser = new FlexibleTimeParser();
  }

  @Test
  void testParseSingleDigitHour() {
    // Given
    String timeString = "9:15";

    // When
    LocalTime result = parser.parse(timeString);

    // Then
    assertEquals(LocalTime.of(9, 15), result);
  }

  @Test
  void testParseDoubleDigitHour() {
    // Given
    String timeString = "14:30";

    // When
    LocalTime result = parser.parse(timeString);

    // Then
    assertEquals(LocalTime.of(14, 30), result);
  }

  @Test
  void testParseMidnight() {
    // Given
    String timeString = "0:00";

    // When
    LocalTime result = parser.parse(timeString);

    // Then
    assertEquals(LocalTime.of(0, 0), result);
  }

  @Test
  void testParseNoon() {
    // Given
    String timeString = "12:00";

    // When
    LocalTime result = parser.parse(timeString);

    // Then
    assertEquals(LocalTime.of(12, 0), result);
  }

  @ParameterizedTest
  @ValueSource(strings = {"23:59", "1:05", "10:45", "00:30"})
  void testParseValidTimes(final String timeString) {
    assertDoesNotThrow(() -> parser.parse(timeString));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "25:00", "12:60", "abc", "12", ":30", "12:", "1:5"})
  void testParseInvalidFormats(final String timeString) {
    assertThrows(DateTimeParseException.class, () -> parser.parse(timeString));
  }
}
