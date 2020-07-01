package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class RotateStringTest {

    @Test
    public void rotate() {
        String A = "abcde";
        String B = "cdeab";
        RotateString rotater = new RotateString();

        assertEquals(true, rotater.rotate(A,B));
    }
}