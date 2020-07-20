package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class FibonacciTest {

    @Test
    public void recursiveTest() {
        Fibonacci fib = new Fibonacci();

        assertEquals(3, fib.useRecursion(4));
        assertEquals(5, fib.useRecursion(5));
    }

    @Test
    public void dynamicTest() {
        Fibonacci fib = new Fibonacci();

        assertEquals(3, fib.useDP(4));
        assertEquals(5, fib.useDP(5));
    }

    @Test
    public void climbingStairTest() {
        Fibonacci fib = new Fibonacci();

        assertEquals(3, fib.climbingStair(3));
        assertEquals(5, fib.climbingStair(4));
    }

}