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
class OClockRuleTest {

    @Mock
    private NumberToWordConverter mockNumberConverter;

    @Mock
    private TimeExpressionRule mockNextRule;

    private OClockRule rule;

    @BeforeEach
    void setUp() {
        rule = new OClockRule(mockNumberConverter);
        rule.setNextRule(mockNextRule);
    }

    @Test
    void testCanHandleExactHours() {
        assertTrue(rule.canHandle(LocalTime.of(1, 0)));
        assertTrue(rule.canHandle(LocalTime.of(14, 0)));
        assertTrue(rule.canHandle(LocalTime.of(23, 0)));
    }

    @Test
    void testCannotHandleNonZeroMinutes() {
        assertFalse(rule.canHandle(LocalTime.of(14, 1)));
        assertFalse(rule.canHandle(LocalTime.of(14, 30)));
        assertFalse(rule.canHandle(LocalTime.of(14, 59)));
    }

    @Test
    void testProcessOClock() {
        // Given
        LocalTime time = LocalTime.of(14, 0);
        when(mockNumberConverter.getHourName(14)).thenReturn("two");

        // When
        String result = rule.process(time);

        // Then
        assertEquals("two o'clock", result);
        verify(mockNumberConverter).getHourName(14);
    }

    @Test
    void testProcessMorningHour() {
        // Given
        LocalTime time = LocalTime.of(9, 0);
        when(mockNumberConverter.getHourName(9)).thenReturn("nine");

        // When
        String result = rule.process(time);

        // Then
        assertEquals("nine o'clock", result);
        verify(mockNumberConverter).getHourName(9);
    }
}

