package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class FindDuplicateTest {

    @Test
    public void useSort() {
        int[] input = {1,3,4,2,2};
        int[] input2 = {3,1,3,4,2};
        FindDuplicate check = new FindDuplicate();

        assertEquals(2, check.useSort(input));
        assertEquals(3, check.useSort(input2));
    }

    @Test
    public void useSet() {
        int[] input = {1,3,4,2,2};
        int[] input2 = {3,1,3,4,2};
        FindDuplicate check = new FindDuplicate();

        assertEquals(2, check.useSet(input));
        assertEquals(3, check.useSet(input2));
    }
}