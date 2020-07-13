package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class DuplicateCheckTest {

    @Test
    public void useSort() {
        int[] input = {1,1,2,4,3,2,5,6,4};
        int[] input2 = {1,2,3,4,5};
        DuplicateCheck check = new DuplicateCheck();

        assertEquals(true, check.useSort(input));
        assertEquals(false, check.useSort(input2));
    }

    @Test
    public void useSet() {
        int[] input = {1,1,2,4,3,2,5,6,4};
        int[] input2 = {1,2,3,4,5};
        DuplicateCheck check = new DuplicateCheck();

        assertEquals(true, check.useSet(input));
        assertEquals(false, check.useSet(input2));
    }
}