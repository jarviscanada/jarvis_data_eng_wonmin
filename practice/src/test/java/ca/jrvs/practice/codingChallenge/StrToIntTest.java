package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class StrToIntTest {

    @Test
    public void withParse() {
        String input = "3451";
        String input2 = "12";
        StrToInt conv = new StrToInt();

        assertEquals(3451, conv.withParse(input));
        assertEquals(12, conv.withParse(input2));
    }

    @Test
    public void pureConversion() {
        String input = "3451";
        String input2 = "12";
        StrToInt conv = new StrToInt();

        assertEquals(3451, conv.pureConversion(input));
        assertEquals(12, conv.pureConversion(input2));
    }
}