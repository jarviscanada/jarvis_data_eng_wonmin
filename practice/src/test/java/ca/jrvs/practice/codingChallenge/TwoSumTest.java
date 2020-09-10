package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class TwoSumTest {

    @Test
    public void bruteForce() {
        TwoSum tester = new TwoSum();
        int[] input = {1, 2, 4, 7};
        int[] result = {0, 2};
        int[] input2 = {2, 1, 6, 4};
        int[] result2 = {2, 3};

        assertArrayEquals(result, tester.bruteForce(input, 5));
        assertArrayEquals(result2, tester.bruteForce(input2, 10));
    }

    @Test
    public void useSort() {
        TwoSum tester = new TwoSum();
        int[] input = {1, 2, 4, 7};
        int[] result = {0, 2};
        int[] input2 = {2, 1, 6, 4};
        int[] result2 = {2, 3};

        assertArrayEquals(result, tester.useSort(input, 5));
        assertArrayEquals(result2, tester.useSort(input2, 10));
    }

    @Test
    public void useMap() {
        TwoSum tester = new TwoSum();
        int[] input = {1, 2, 4, 7};
        int[] result = {0, 2};
        int[] input2 = {2, 1, 6, 4};
        int[] result2 = {2, 3};

        assertArrayEquals(result, tester.useMap(input, 5));
        assertArrayEquals(result2, tester.useMap(input2, 10));
    }

}