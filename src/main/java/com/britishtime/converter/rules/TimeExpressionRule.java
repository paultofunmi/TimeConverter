package com.britishtime.converter.rules;

import java.time.LocalTime;

/**
 * Abstract base class for time expression rules using Chain of Responsibility pattern. Each rule
 * handles specific time patterns and delegates to the next rule if not applicable.
 */
public abstract class TimeExpressionRule {

  private TimeExpressionRule nextRule;

  public void setNextRule(final TimeExpressionRule nextRule) {
    this.nextRule = nextRule;
  }

  public String handle(final LocalTime time) {
    if (canHandle(time)) {
      return process(time);
    }
    if (nextRule != null) {
      return nextRule.handle(time);
    }
    throw new IllegalStateException("No rule can handle time: " + time);
  }

  protected abstract boolean canHandle(LocalTime time);

  protected abstract String process(LocalTime time);
}
