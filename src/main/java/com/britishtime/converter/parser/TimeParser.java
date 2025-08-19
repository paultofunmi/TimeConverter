package com.britishtime.converter.parser;

import java.time.LocalTime;

public interface TimeParser {
    LocalTime parse(String timeString);
}
