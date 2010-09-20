/*
 * Copyright 2008 the original author or authors.
 */
package cz.silesnet.sis.util;

import java.util.regex.Matcher;

/**
 * Regular expression utility class.
 *
 * @author Richard Sikora
 */
public class RegExpUtils {

  /**
   * Returns first matching group of the matcher, null otherwise.
   *
   * @param matcher initialized matcher with at least one group defined
   * @return first matching group or null
   */
  public static String getMatcherGroup(Matcher matcher, int group) {
    return matcher.matches() ? matcher.group(group) : null;
  }

  public static String getFirstMatcherGroup(Matcher matcher) {
    return getMatcherGroup(matcher, 1);
  }
}
