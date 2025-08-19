package com.britishtime.converter.speech;

import java.time.LocalTime;

public interface SpeechConverter {
    String convertToSpeech(LocalTime time);
}