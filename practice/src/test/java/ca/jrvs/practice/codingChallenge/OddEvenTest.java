package ca.jrvs.practice.codingChallenge;

import org.junit.Test;
import static org.junit.Assert.*;

public class OddEvenTest {

    @Test
    public void evenOddMod() {
        OddEven od = new OddEven();
        assertEquals("Even", od.evenOddMod(6));
        assertEquals("Odd", od.evenOddMod(5));
    }

    @Test
    public void evenOddBit() {
        OddEven od = new OddEven();
        assertEquals("Even", od.evenOddBit(6));
        assertEquals("Odd", od.evenOddBit(5));
    }
}