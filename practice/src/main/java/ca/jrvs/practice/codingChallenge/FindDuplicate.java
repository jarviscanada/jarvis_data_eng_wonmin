package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Ticket: https://www.notion.so/Find-the-Duplicate-Number-b4caa2322d4343dc9aab6d690b16c6e5
 */

public class FindDuplicate {

    /**
     * Big-O: O(nlog(n))
     * Justification: Arrays.sort has a time complexity of O(nlog(n))
     */
    public int useSort(int[] input) {
        Arrays.sort(input);
        int dupNum = 0;

        for (int i = 0; i < input.length - 1; i++) {
            if (input[i] == input[i + 1]) {
                dupNum = input[i];
            }
        }
        return dupNum;
    }

    /**
     * Big-O: O(n)
     * Justification: The algorithm iterates through array once and checks for duplicates
     */
    public int useSet(int[] input) {
        Set mySet = new HashSet();
        int dupNum = 0;

        for (int i : input) {
            if (mySet.contains(i)) {
                dupNum = i;
            }
            mySet.add(i);
        }
        return dupNum;
    }
}

