package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class SortedArrayDuplicateTest {

    @Test
    public void removeDup() {
        int[] input = {0,1,1,2,2,3,4};
        SortedArrayDuplicate tester = new SortedArrayDuplicate();

        assertEquals(5, tester.removeDup(input));
    }
}