package ca.jrvs.practice.dataStructure;

import java.util.Arrays;
import java.util.Optional;

public class BinarySearch {

    /**
     * find the the target index in a sorted array using recursion
     *
     * @param arr input arry is sorted
     * @param target value to be searched
     * @return target index or Optional.empty() if not found
     */
    public <E extends Comparable<E>> Optional<Integer> binarySearchRecursion(E[] arr, E target) {
        return internalRecurBinSearch(arr,0,arr.length-1, target);
    }

    /**
     * internal method for binary search using recursion
     * @param arr input array
     * @param start starting index of array
     * @param end end index of array
     * @param target value to be searched
     * @return target index
     */
    public <E extends Comparable<E>> Optional<Integer> internalRecurBinSearch(E[] arr, int start, int end, E target) {
        int midPoint = (start + end) / 2;
        Optional<Integer> mid = Optional.of(midPoint);

        if (start > end) {
            return Optional.empty();
        }
        if (target == null) {
            return Optional.empty();
        }

        if (target.equals(arr[midPoint])) {
            return mid;
        }
        else if (target.compareTo(arr[midPoint]) > 0) {
            return internalRecurBinSearch(arr, midPoint+1, end, target);
        }
        else if (target.compareTo(arr[midPoint]) < 0) {
            return internalRecurBinSearch(arr, start, midPoint-1, target);
        }
        return Optional.empty();
    }

    /**
     * find the the target index in a sorted array using iteration
     *
     * @param arr input arry is sorted
     * @param target value to be searched
     * @return target index or Optional.empty() if not ound
     */
    public <E extends Comparable<E>> Optional<Integer> binarySearchIteration(E[] arr, E target) {
        return internalItBinSearch(arr, target);
    }

    /**
     * internal method for binary search using iteration
     * @param arr input array
     * @param target value to be searched
     * @return target index
     */
    public <E extends Comparable<E>> Optional<Integer> internalItBinSearch(E[] arr, E target) {
        int low = 0;
        int high = arr.length -1;
        Optional<Integer> targ = Optional.of(0);

        while (low <= high) {
            int midPoint = (low + high) / 2;
            int cmp = target.compareTo(arr[midPoint]);

            if (cmp == 0) {
                targ = Optional.of(midPoint);
                return targ;
            } else if (cmp < 0) {
                high = midPoint - 1;
            } else {
                low = midPoint + 1;
            }
        }
        return Optional.empty();
    }
}



