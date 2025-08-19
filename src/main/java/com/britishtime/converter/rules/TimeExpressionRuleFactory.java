package com.britishtime.converter.rules;

import com.britishtime.converter.rules.impl.OClockRule;
import com.britishtime.converter.rules.impl.PastHourRule;
import com.britishtime.converter.rules.impl.SpecialTimeRule;
import com.britishtime.converter.rules.impl.ToHourRule;
import com.britishtime.converter.util.NumberToWordConverter;

/**
 * Factory for creating and configuring the chain of time expression rules.
 * Encapsulates the complexity of rule chain setup.
 */
public class TimeExpressionRuleFactory {

    public static TimeExpressionRule createBritishRuleChain() {
        NumberToWordConverter numberConverter = new NumberToWordConverter();

        // Create rules
        SpecialTimeRule specialRule = new SpecialTimeRule();
        OClockRule oClockRule = new OClockRule(numberConverter);
        PastHourRule pastHourRule = new PastHourRule(numberConverter);
        ToHourRule toHourRule = new ToHourRule(numberConverter);

        // Ordering/Chaining the rules
        specialRule.setNextRule(oClockRule);
        oClockRule.setNextRule(pastHourRule);
        pastHourRule.setNextRule(toHourRule);

        return specialRule;
    }
}
