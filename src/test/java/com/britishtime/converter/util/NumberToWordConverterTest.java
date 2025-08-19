package com.britishtime.converter.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NumberToWordConverterTest {

  private NumberToWordConverter converter;

  @BeforeEach
  void setUp() {
    converter = new NumberToWordConverter();
  }

  @ParameterizedTest
  @CsvSource({"0, twelve", "1, one", "2, two", "11, eleven", "12, twelve", "13, one", "23, eleven"})
  @DisplayName("Test getHourName(int) for teens")
  void testGetHourName(final int hour, final String expected) {
    assertEquals(expected, converter.getHourName(hour));
  }

  @ParameterizedTest
  @CsvSource({
    "0, ''",
    "1, one",
    "15, fifteen",
    "20, twenty",
    "21, twenty one",
    "25, twenty five",
    "30, thirty",
    "45, forty five",
    "59, fifty nine"
  })
  void testGetMinuteName(final int minute, final String expected) {
    assertEquals(expected, converter.getMinuteName(minute));
  }

  @Test
  @DisplayName("Test getMinuteName(int) for tens")
  void testGetMinuteNameForTens() {
    assertEquals("twenty", converter.getMinuteName(20));
    assertEquals("thirty", converter.getMinuteName(30));
    assertEquals("forty", converter.getMinuteName(40));
    assertEquals("fifty", converter.getMinuteName(50));
  }

  @Test
  @DisplayName("Test getMinuteName(int) for teens")
  void testGetMinuteNameForTeens() {
    assertEquals("ten", converter.getMinuteName(10));
    assertEquals("eleven", converter.getMinuteName(11));
    assertEquals("twelve", converter.getMinuteName(12));
    assertEquals("thirteen", converter.getMinuteName(13));
    assertEquals("nineteen", converter.getMinuteName(19));
  }
}
