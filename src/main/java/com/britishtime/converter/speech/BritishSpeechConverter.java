package com.britishtime.converter.speech;

import com.britishtime.converter.rules.TimeExpressionRule;
import com.britishtime.converter.rules.TimeExpressionRuleFactory;
import java.time.LocalTime;

/**
 * British implementation of speech conversion using CoR for rules.
 */
public class BritishSpeechConverter implements SpeechConverter {

    private final TimeExpressionRule ruleChain;

    public BritishSpeechConverter() {
        this.ruleChain = TimeExpressionRuleFactory.createBritishRuleChain();
    }

    // Constructor for testing - Dependency Injection
    public BritishSpeechConverter(TimeExpressionRule ruleChain) {
        this.ruleChain = ruleChain;
    }

    @Override
    public String convertToSpeech(LocalTime time) {
        return ruleChain.handle(time);
    }
}
