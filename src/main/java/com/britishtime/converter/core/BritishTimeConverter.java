package com.britishtime.converter.core;

import com.britishtime.converter.parser.FlexibleTimeParser;
import com.britishtime.converter.parser.TimeParser;
import com.britishtime.converter.speech.BritishSpeechConverter;
import com.britishtime.converter.speech.SpeechConverter;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Main facade orchestrating the conversion process
 */
public class BritishTimeConverter {

    private final TimeParser timeParser;
    private final SpeechConverter speechConverter;

    public BritishTimeConverter() {
        this.timeParser = new FlexibleTimeParser();
        this.speechConverter = new BritishSpeechConverter();
    }

    public BritishTimeConverter(TimeParser timeParser, SpeechConverter speechConverter) {
        this.timeParser = timeParser;
        this.speechConverter = speechConverter;
    }

    /**
     * Facade method that orchestrates the entire conversion process
     */
    public String convert(String timeInput) {
        if (timeInput == null || timeInput.trim().isEmpty()) {
            throw new IllegalArgumentException("Time input cannot be null or empty");
        }

        try {
            LocalTime time = timeParser.parse(timeInput.trim());
            return speechConverter.convertToSpeech(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(String.format("Invalid time format: %s. Expected format: HH:mm or H:mm", timeInput), e);
        }
    }
}
