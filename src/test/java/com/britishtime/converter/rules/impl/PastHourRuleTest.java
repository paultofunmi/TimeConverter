package com.britishtime.converter.rules.impl;

import com.britishtime.converter.rules.TimeExpressionRule;
import com.britishtime.converter.util.NumberToWordConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PastHourRuleTest {

    @Mock
    private NumberToWordConverter mockNumberConverter;

    @Mock
    private TimeExpressionRule mockNextRule;

    private PastHourRule rule;

    @BeforeEach
    void setUp() {
        rule = new PastHourRule(mockNumberConverter);
        rule.setNextRule(mockNextRule);
    }

    @Test
    void testCanHandleMinutesFromOneToThirty() {
        assertTrue(rule.canHandle(LocalTime.of(14, 1)));
        assertTrue(rule.canHandle(LocalTime.of(14, 15)));
        assertTrue(rule.canHandle(LocalTime.of(14, 30)));
    }

    @Test
    void testCannotHandleZeroMinutes() {
        assertFalse(rule.canHandle(LocalTime.of(14, 0)));
    }

    @Test
    void testCannotHandleMinutesGreaterThan30() {
        assertFalse(rule.canHandle(LocalTime.of(14, 31)));
        assertFalse(rule.canHandle(LocalTime.of(14, 45)));
    }

    @Test
    void testProcessQuarterPast() {
        // Given
        LocalTime time = LocalTime.of(14, 15);
        when(mockNumberConverter.getHourName(14)).thenReturn("two");

        // When
        String result = rule.process(time);

        // Then
        assertEquals("quarter past two", result);
        verify(mockNumberConverter).getHourName(14);
    }

    @Test
    void testProcessHalfPast() {
        // Given
        LocalTime time = LocalTime.of(14, 30);
        when(mockNumberConverter.getHourName(14)).thenReturn("two");

        // When
        String result = rule.process(time);

        // Then
        assertEquals("half past two", result);
        verify(mockNumberConverter).getHourName(14);
    }

    @Test
    void testProcessRegularMinutesPast() {
        // Given
        LocalTime time = LocalTime.of(14, 10);
        when(mockNumberConverter.getHourName(14)).thenReturn("two");
        when(mockNumberConverter.getMinuteName(10)).thenReturn("ten");

        // When
        String result = rule.process(time);

        // Then
        assertEquals("ten past two", result);
        verify(mockNumberConverter).getHourName(14);
        verify(mockNumberConverter).getMinuteName(10);
    }

    @Test
    void testProcessTwentyFiveMinutesPast() {
        // Given
        LocalTime time = LocalTime.of(14, 25);
        when(mockNumberConverter.getHourName(14)).thenReturn("two");
        when(mockNumberConverter.getMinuteName(25)).thenReturn("twenty five");

        // When
        String result = rule.process(time);

        // Then
        assertEquals("twenty five past two", result);
    }

    @Test
    void testProcessIrregularMinutes() {
        // Given - testing minutes > 25 and not 30 (should use "hour minute" format)
        LocalTime time = LocalTime.of(14, 27);
        when(mockNumberConverter.getHourName(14)).thenReturn("two");
        when(mockNumberConverter.getMinuteName(27)).thenReturn("twenty seven");

        // When
        String result = rule.process(time);

        // Then
        assertEquals("two twenty seven", result);
    }
}


