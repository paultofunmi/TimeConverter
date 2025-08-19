package com.britishtime.converter;

import com.britishtime.converter.core.BritishTimeConverter;
import com.britishtime.converter.ui.impl.ConsoleInterface;

/**
 * Main application class
 * serving as the entry point
 * &amp; facade for the British Time Conversion system.
 *
 */
public class BritishTimeConverterApplication {

    public static void main(String[] args) {
        BritishTimeConverter converter = new BritishTimeConverter();
        ConsoleInterface console = new ConsoleInterface(converter);
        console.start();
    }
}