package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class SwapTwoTest {

    @Test
    public void swapTwo() {
        SwapTwo swap = new SwapTwo();
        int[] input = {3,4};
        int[] input2 = {1,2};
        int[] expect = {4,3};
        int[] expect2 = {2,1};

        assertArrayEquals(expect, swap.swapTwoArith(input));
        assertArrayEquals(expect2, swap.swapTwoBit(input2));
    }
}