package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidAnagramTest {

    @Test
    public void useSort() {
        String input = "anagram";
        String compare = "nagaram";
        String input2 = "rat";
        String compare2 = "car";
        ValidAnagram tester = new ValidAnagram();

        assertEquals(true, tester.useSort(input, compare));
        assertEquals(false, tester.useSort(input2, compare2));
    }

    @Test
    public void useMap() {
        String input = "anagram";
        String compare = "nagaram";
        String input2 = "rat";
        String compare2 = "car";
        ValidAnagram tester = new ValidAnagram();

        assertEquals(true, tester.useMap(input, compare));
        assertEquals(false, tester.useMap(input2, compare2));
    }
}