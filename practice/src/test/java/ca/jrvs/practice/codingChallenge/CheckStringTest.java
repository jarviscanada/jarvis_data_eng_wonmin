package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class CheckStringTest {

    @Test
    public void useASCII() {
        String input = "1234";
        String input2 = "123,456";
        String input3 = "123AB";
        CheckString checker = new CheckString();

        assertEquals(true, checker.useASCII(input));
        assertEquals(false, checker.useASCII(input2));
        assertEquals(false, checker.useASCII(input3));
    }

    @Test
    public void useAPI() {
        String input = "1234";
        String input2 = "123,456";
        String input3 = "123AB";
        CheckString checker = new CheckString();

        assertEquals(true, checker.useAPI(input));
        assertEquals(false, checker.useAPI(input2));
        assertEquals(false, checker.useAPI(input3));
    }

    @Test
    public void useRegex() {
        String input = "1234";
        String input2 = "123,456";
        String input3 = "123AB";
        CheckString checker = new CheckString();

        assertEquals(true, checker.useRegex(input));
        assertEquals(false, checker.useRegex(input2));
        assertEquals(false, checker.useRegex(input3));
    }


}