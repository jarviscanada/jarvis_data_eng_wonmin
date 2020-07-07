package ca.jrvs.practice.dataStructure;

import org.junit.Test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class SortTest {

    @Test
    public void quickSort() {
        int[] items = {22, 55, 66, 11, 32, 56, 67, 89, 95, 10 };
        int[] sortedItems = {10, 11, 22, 32, 55, 56, 66, 67, 89, 95};
        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(items,0,items.length-1);
        Arrays.stream(items).forEach(System.out::println);
        assertArrayEquals(items, sortedItems);
    }

    @Test
    public void mergeSort() {
        int[] items = {22, 55, 66, 11, 32, 56, 67, 89, 95, 10 };
        int[] sortedItems = {10, 11, 22, 32, 55, 56, 66, 67, 89, 95};
        MergeSort.mergeSort(items, items.length);
        Arrays.stream(items).forEach(System.out::println);
        assertArrayEquals(items, sortedItems);
    }



}