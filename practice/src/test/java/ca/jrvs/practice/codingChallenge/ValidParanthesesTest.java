package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidParanthesesTest {

    @Test
    public void validParentheses() {
        String input = "[()]";
        String input2 = "()[]{}";
        String input3 = "([)]";
        ValidParantheses tester = new ValidParantheses();

        assertEquals(true, tester.isValid(input));
        assertEquals(true, tester.isValid(input2));
        assertEquals(false, tester.isValid(input3));
    }

}