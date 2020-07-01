package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;


public class MergeArrayTest {

    @Test
    public void Merge() {
        int[] firstArray = {1, 2, 3, 0, 0, 0};
        int[] secondArray = {2, 5, 6};
        int[] sortedArray = {1, 2, 2, 3, 5, 6};
        MergeArray merger = new MergeArray();
        merger.merge(firstArray, 3, secondArray, 3);

        assertArrayEquals(sortedArray, firstArray);
    }

}