package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class MissingNumberTest {

    @Test
    public void sumAll() {
        int[] input = {0,1,3,4};
        int[] input2 = {2,0,4,3,1,6};
        int[] input3 = {2,0,4,3,1,6};
        MissingNumber tester = new MissingNumber();

        assertEquals(2, tester.sumAll(input));
        assertEquals(5, tester.sumAll(input2));
    }

    @Test
    public void useSet() {
        int[] input = {0,1,3,4};
        int[] input2 = {2,0,4,3,1,6};
        MissingNumber tester = new MissingNumber();

        assertEquals(2, tester.useSet(input));
        assertEquals(5, tester.useSet(input2));
    }

    @Test
    public void useGauss() {
        int[] input = {0,1,3,4};
        int[] input2 = {2,0,4,3,1,6};
        MissingNumber tester = new MissingNumber();

        assertEquals(2, tester.useGauss(input));
        assertEquals(5, tester.useGauss(input2));
    }

}