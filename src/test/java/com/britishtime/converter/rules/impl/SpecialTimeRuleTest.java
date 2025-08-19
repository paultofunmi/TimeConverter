package com.britishtime.converter.rules.impl;

import com.britishtime.converter.rules.TimeExpressionRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SpecialTimeRuleTest {

    @Mock
    private TimeExpressionRule mockNextRule;

    private SpecialTimeRule rule;

    @BeforeEach
    void setUp() {
        rule = new SpecialTimeRule();
        rule.setNextRule(mockNextRule);
    }

    @Test
    void testCanHandleMidnight() {
        assertTrue(rule.canHandle(LocalTime.of(0, 0)));
    }

    @Test
    void testCanHandleNoon() {
        assertTrue(rule.canHandle(LocalTime.of(12, 0)));
    }

    @Test
    void testCannotHandleRegularTimes() {
        assertFalse(rule.canHandle(LocalTime.of(1, 0)));
        assertFalse(rule.canHandle(LocalTime.of(0, 30)));
        assertFalse(rule.canHandle(LocalTime.of(12, 30)));
        assertFalse(rule.canHandle(LocalTime.of(14, 0)));
    }

    @Test
    void testProcessMidnight() {
        // Given
        LocalTime time = LocalTime.of(0, 0);

        // When
        String result = rule.process(time);

        // Then
        assertEquals("midnight", result);
    }

    @Test
    void testProcessNoon() {
        // Given
        LocalTime time = LocalTime.of(12, 0);

        // When
        String result = rule.process(time);

        // Then
        assertEquals("noon", result);
    }
}

