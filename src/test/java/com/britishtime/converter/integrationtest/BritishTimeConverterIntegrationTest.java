package com.britishtime.converter.integrationtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.britishtime.converter.core.BritishTimeConverter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for the complete time conversion flow.
 * Tests the entire pipeline from string input to speech output.
 */
class BritishTimeConverterIntegrationTest {

    private BritishTimeConverter converter;

    @BeforeEach
    void setUp() {
        // Use real implementations, no mocks
        converter = new BritishTimeConverter();
    }

    @ParameterizedTest
    @CsvSource({
            // Special times
            "0:00, midnight",
            "12:00, noon",

            // O'clock times
            "1:00, one o'clock",
            "13:00, one o'clock",
            "9:00, nine o'clock",
            "21:00, nine o'clock",

            // Quarter past times
            "1:15, quarter past one",
            "14:15, quarter past two",
            "23:15, quarter past eleven",

            // Half past times
            "2:30, half past two",
            "14:30, half past two",
            "23:30, half past eleven",

            // Quarter to times
            "2:45, quarter to three",
            "14:45, quarter to three",
            "23:45, quarter to twelve",

            // Minutes past (1-20, 25)
            "9:05, five past nine",
            "14:10, ten past two",
            "16:20, twenty past four",
            "18:25, twenty five past six",

            // Minutes to (31-59, excluding 45)
            "9:35, twenty five to ten",
            "14:40, twenty to three",
            "16:50, ten to five",
            "18:55, five to seven",

            // Edge cases with single digit hours
            "1:05, five past one",
            "9:15, quarter past nine",
            "8:45, quarter to nine",

            // Midnight transitions
            "23:50, ten to twelve",
            "23:45, quarter to twelve"
    })
    void testCompleteTimeConversionFlow(String input, String expectedOutput) {
        String result = converter.convert(input);
        assertEquals(expectedOutput, result);
    }

    @Test
    void testInvalidInputHandling() {
        // Test various invalid inputs
        assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
        assertThrows(IllegalArgumentException.class, () -> converter.convert(""));
        assertThrows(IllegalArgumentException.class, () -> converter.convert("   "));
        assertThrows(IllegalArgumentException.class, () -> converter.convert("25:00"));
        assertThrows(IllegalArgumentException.class, () -> converter.convert("12:60"));
        assertThrows(IllegalArgumentException.class, () -> converter.convert("abc"));
        assertThrows(IllegalArgumentException.class, () -> converter.convert("12"));
        assertThrows(IllegalArgumentException.class, () -> converter.convert("1:5"));
    }

    @Test
    void testWhitespaceHandling() {
        // Test that whitespace is properly handled
        assertEquals("quarter past two", converter.convert("  14:15  "));
        assertEquals("half past nine", converter.convert("\t9:30\n"));
        assertEquals("noon", converter.convert(" 12:00 "));
    }

    @Test
    void testAllHoursOfDay() {
        // Test conversion for every hour of the day (o'clock times)
        String[] expectedHours = {
                "twelve", "one", "two", "three", "four", "five",
                "six", "seven", "eight", "nine", "ten", "eleven"
        };

        for (int hour = 1; hour < 24; hour++) {
            if (hour == 12) {
                assertEquals("noon", converter.convert(hour + ":00"));
            } else {
                String input = String.format("%d:00", hour);
                String expected = expectedHours[hour % 12] + " o'clock";
                assertEquals(expected, converter.convert(input),
                        "Failed for hour: " + hour);
            }
        }

        // Test midnight separately
        assertEquals("midnight", converter.convert("0:00"));
    }

    @Test
    void testComplexMinuteRanges() {
        // Test all minute ranges to ensure proper rule application

        // Test minutes 1-30 (past rules)
        for (int minute = 1; minute <= 30; minute++) {
            String input = String.format("14:%02d", minute);
            String result = converter.convert(input);

            if (minute == 15) {
                assertEquals("quarter past two", result);
            } else if (minute == 30) {
                assertEquals("half past two", result);
            } else {
                assertNotNull(result);
                assertTrue(result.contains("past") || result.contains("two"),
                        "Unexpected result for " + input + ": " + result);
            }
        }

        // Test minutes 31-59 (to rules)
        for (int minute = 31; minute <= 59; minute++) {
            String input = String.format("14:%02d", minute);
            String result = converter.convert(input);

            if (minute == 45) {
                assertEquals("quarter to three", result);
            } else {
                assertNotNull(result);
                assertTrue(result.contains("to"),
                        "Unexpected result for " + input + ": " + result);
            }
        }
    }
}
