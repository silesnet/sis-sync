package cz.silesnet.sis.util;

import static org.junit.Assert.assertEquals;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegExpUtilsTest {

    private static String TEXT = "aaabbbcccdddeee";
    private static Pattern PATTERN = Pattern.compile(".*(bbb).*(ddd).*");

    private Matcher matcher;

    @Before
    public void setUp() throws Exception {
        matcher = PATTERN.matcher(TEXT);
    }

    @After
    public void tearDown() throws Exception {
        matcher = null;
    }

    @Test
    public void testFirstMatcherGroup() throws Exception {
        assertEquals("bbb", RegExpUtils.getFirstMatcherGroup(matcher));
    }

    @Test
    public void testSecondMatcherGroup() throws Exception {
        assertEquals("ddd", RegExpUtils.getMatcherGroup(matcher, 2));
    }

}
