package com.britishtime.converter.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.britishtime.converter.parser.TimeParser;
import com.britishtime.converter.speech.SpeechConverter;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BritishTimeConverterTest {

  @Mock private TimeParser mockTimeParser;

  @Mock private SpeechConverter mockSpeechConverter;

  private BritishTimeConverter converter;

  @BeforeEach
  void setUp() {
    converter = new BritishTimeConverter(mockTimeParser, mockSpeechConverter);
  }

  @Test
  void testDefaultConstructor() {
    BritishTimeConverter defaultConverter = new BritishTimeConverter();
    assertNotNull(defaultConverter);
  }

  @Test
  void testConvertValidInput() {
    // Given
    String input = "14:30";
    LocalTime time = LocalTime.of(14, 30);
    String expected = "half past two";

    when(mockTimeParser.parse(input)).thenReturn(time);
    when(mockSpeechConverter.convertToSpeech(time)).thenReturn(expected);

    // When
    String result = converter.convert(input);

    // Then
    assertEquals(expected, result);
    verify(mockTimeParser).parse(input);
    verify(mockSpeechConverter).convertToSpeech(time);
  }

  @Test
  void testConvertWithWhitespace() {
    // Given
    String input = "  09:15  ";
    String trimmedInput = "09:15";
    LocalTime time = LocalTime.of(9, 15);
    String expected = "quarter past nine";

    when(mockTimeParser.parse(trimmedInput)).thenReturn(time);
    when(mockSpeechConverter.convertToSpeech(time)).thenReturn(expected);

    // When
    String result = converter.convert(input);

    // Then
    assertEquals(expected, result);
    verify(mockTimeParser).parse(trimmedInput);
  }

  @Test
  void testConvertNullInput() {
    // When & Then
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
    assertEquals("Time input cannot be null or empty", exception.getMessage());
  }

  @Test
  void testConvertEmptyInput() {
    // When & Then
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> converter.convert(""));
    assertEquals("Time input cannot be null or empty", exception.getMessage());
  }

  @Test
  void testConvertWhitespaceOnlyInput() {
    // When & Then
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> converter.convert("   "));
    assertEquals("Time input cannot be null or empty", exception.getMessage());
  }

  @Test
  void testConvertInvalidTimeFormat() {
    // Given
    String input = "invalid";
    when(mockTimeParser.parse(input))
        .thenThrow(new DateTimeParseException("Invalid format", input, 0));

    // When & Then
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> converter.convert(input));
    assertTrue(exception.getMessage().contains("Invalid time format: invalid"));
    assertTrue(exception.getMessage().contains("Expected format: HH:mm or H:mm"));
  }
}
