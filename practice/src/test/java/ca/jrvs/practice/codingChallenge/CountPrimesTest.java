package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountPrimesTest {

    @Test
    public void countPrimes() {
        CountPrimes count = new CountPrimes();
        assertEquals(25, count.countPrimes(100));
        assertEquals(5, count.countPrimes(12));
        assertEquals(2, count.countPrimes(5));
    }
}