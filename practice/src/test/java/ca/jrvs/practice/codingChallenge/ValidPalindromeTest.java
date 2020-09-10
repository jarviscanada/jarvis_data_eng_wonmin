package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidPalindromeTest {

    @Test
    public void useTwoPointers() {
        String input = "IdiddidI";
        String input2 = "TopspoT";
        ValidPalindrome tester = new ValidPalindrome();

        assertEquals(true, tester.useTwoPointer(input));
        assertEquals(true, tester.useTwoPointer(input2));
    }

    @Test
    public void useRecursion() {
        String input = "IdiddidI";
        String input2 = "TopspoT";
        ValidPalindrome tester = new ValidPalindrome();

        assertEquals(true, tester.useRecursion(input));
        assertEquals(true, tester.useRecursion(input2));
    }
}