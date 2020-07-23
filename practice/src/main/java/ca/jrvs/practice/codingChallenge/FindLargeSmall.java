package ca.jrvs.practice.codingChallenge;

import java.util.*;

/**
 * Ticket: https://www.notion.so/Find-Largest-Smallest-c716d27c8dec4ab292366db25ff3b61a
 */

public class FindLargeSmall {
    /**
     * Big-O: O(n)
     * Justification: It loops through the array once
     */
    public int oneLoop (int[] input) {
        int max = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] > max) {
                max = input[i];
            }
        }
        return max;
    }
    /**
     * Big-O: O(n)
     * Justification: The Stream api still uses internal iteration, hence we are still iterating through the array
     */
    public int useStream(int[] input) {
        int max = Arrays.stream(input).max().getAsInt();
        return max;
    }

    /**
     * Big-O: O(n)
     * Justification: Converting the primitive array to to an ArrayList and using collections
     */
    public int useAPI(int[] input) {
        List<Integer> arr = new ArrayList();
        for (int i = 0; i < input.length; i++) {
            arr.add(input[i]);
        }
        return Collections.max(arr);
    }


}

