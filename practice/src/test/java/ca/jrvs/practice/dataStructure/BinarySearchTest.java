package ca.jrvs.practice.dataStructure;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Arrays;

public class BinarySearchTest {

    @Test
    public void binarySearchRecursion() {
        Integer[] items = { 22, 55, 66, 11, 32, 56, 67, 89, 95, 10 };
        Arrays.sort(items);
        BinarySearch bin = new BinarySearch();

        int firstElement = bin.binarySearchRecursion(items,Integer.valueOf(10)).get();
        int lastElement = bin.binarySearchRecursion(items,Integer.valueOf(95)).get();

        assertEquals(0, firstElement);
        assertEquals(9, lastElement);
    }

    @Test
    public void binarySearchIteration() {
        Integer[] items = { 22, 55, 66, 11, 32, 56, 67, 89, 95, 10 };
        Arrays.sort(items);
        BinarySearch bin = new BinarySearch();

        int firstElement = bin.binarySearchIteration(items,Integer.valueOf(10)).get();
        int lastElement = bin.binarySearchIteration(items,Integer.valueOf(95)).get();

        assertEquals(0, firstElement);
        assertEquals(9, lastElement);
    }

}