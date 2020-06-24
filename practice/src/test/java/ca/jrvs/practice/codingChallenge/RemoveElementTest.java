package ca.jrvs.practice.codingChallenge;

import org.junit.Test;
import static org.junit.Assert.*;

public class RemoveElementTest {

    @Test
    public void removeElement() {
        int[] input = {1,4,5,4,2,7,10};
        RemoveElement remove = new RemoveElement();
        assertEquals(5,remove.removeElement(input,4));
    }
}