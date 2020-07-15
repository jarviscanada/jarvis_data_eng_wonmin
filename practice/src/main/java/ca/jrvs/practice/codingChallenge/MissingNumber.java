package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Ticket: https://www.notion.so/Missing-Number-ffeea46204944be698871b43c919da1b
 */

public class MissingNumber {

    /**
     * Big-O: O(n)
     * Justification: Iterate once through the array to sum all elements
     */
    public int sumAll(int[] input) {
        int missingNum = 0;
        for (int i = 0; i < input.length; ++i) {
            missingNum += (i+1)-input[i];
        }
        return missingNum;
    }

    /**
     * Big-O: O(n)
     * Justification: Loops through the array once to check set contains value
     */
    public int useSet(int[] input) {
        int missingNum = 0;
        Set mySet = new HashSet();
        for (int i:input) {
            mySet.add(i);
        }

        for (int i = 0; i < input.length+1; i++) {
            if (mySet.contains(i) == false) {
                missingNum = i;
            }
        }
        return missingNum;
    }

    /**
     * Big-O: O(n)
     * Justification: Iterate once through the array to sum all elements
     */
    public int useGauss(int[] input) {
        int actualSum = (input.length * (input.length + 1)) / 2;
        int arraySum = 0;
        for (int i:input) {
            arraySum = arraySum + i;
        }
        return actualSum - arraySum;
    }


}

