package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class FindLargeSmallTest {

    @Test
    public void oneLoop() {
        int[] input = {2,4,7,3};
        FindLargeSmall find = new FindLargeSmall();
        assertEquals(7,find.oneLoop(input));
    }

    @Test
    public void useStream() {
        int[] input = {2,4,7,3};
        FindLargeSmall find = new FindLargeSmall();
        assertEquals(7,find.useStream(input));
    }

    @Test
    public void useAPI() {
        int[] input = {2,4,7,3};
        FindLargeSmall find = new FindLargeSmall();
        assertEquals(7, find.useAPI(input));
    }
}