package com.britishtime.converter.rules.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.britishtime.converter.rules.TimeExpressionRule;
import com.britishtime.converter.util.NumberToWordConverter;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ToHourRuleTest {

  @Mock private NumberToWordConverter mockNumberConverter;

  @Mock private TimeExpressionRule mockNextRule;

  private ToHourRule rule;

  @BeforeEach
  void setUp() {
    rule = new ToHourRule(mockNumberConverter);
    rule.setNextRule(mockNextRule);
  }

  @Test
  void testCanHandleMinutesGreaterThan30() {
    assertTrue(rule.canHandle(LocalTime.of(14, 31)));
    assertTrue(rule.canHandle(LocalTime.of(14, 45)));
    assertTrue(rule.canHandle(LocalTime.of(14, 59)));
  }

  @Test
  void testCannotHandleMinutes30OrLess() {
    assertFalse(rule.canHandle(LocalTime.of(14, 0)));
    assertFalse(rule.canHandle(LocalTime.of(14, 15)));
    assertFalse(rule.canHandle(LocalTime.of(14, 30)));
  }

  @Test
  void testProcessQuarterTo() {
    // Given
    LocalTime time = LocalTime.of(14, 45); // 2:45 PM
    when(mockNumberConverter.getHourName(15)).thenReturn("three");

    // When
    String result = rule.process(time);

    // Then
    assertEquals("quarter to three", result);
    verify(mockNumberConverter).getHourName(15);
  }

  @Test
  void testProcessRegularMinutesTo() {
    // Given
    LocalTime time = LocalTime.of(14, 40); // 2:40 PM -> 20 minutes to 3
    when(mockNumberConverter.getHourName(15)).thenReturn("three");
    when(mockNumberConverter.getMinuteName(20)).thenReturn("twenty");

    // When
    String result = rule.process(time);

    // Then
    assertEquals("twenty to three", result);
    verify(mockNumberConverter).getHourName(15);
    verify(mockNumberConverter).getMinuteName(20);
  }

  @Test
  void testProcessMidnightTransition() {
    // Given
    LocalTime time = LocalTime.of(23, 50); // 11:50 PM -> 10 minutes to midnight
    when(mockNumberConverter.getHourName(0)).thenReturn("twelve");
    when(mockNumberConverter.getMinuteName(10)).thenReturn("ten");

    // When
    String result = rule.process(time);

    // Then
    assertEquals("ten to twelve", result);
    verify(mockNumberConverter).getHourName(0);
  }
}
