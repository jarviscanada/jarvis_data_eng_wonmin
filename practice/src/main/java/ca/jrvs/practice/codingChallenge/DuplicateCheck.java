package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Ticket: https://www.notion.so/Contains-Duplicate-29db524c796a4679994ba3cdadadb28c
 */

public class DuplicateCheck {

    /**
     * Big-O: O(nlog(n))
     * Justification: Arrays.sort has a time complexity of O(nlog(n))
     */
    public boolean useSort(int[] input) {
        boolean flag = false;
        Arrays.sort(input);

        for (int i=0; i<input.length-1;i++) {
            if (input[i] == input[i+1]) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Big-O: O(n)
     * Justification: The algorithm iterates through array once and checks for duplicates
     */
    public boolean useSet(int[] input) {
        Set mySet = new HashSet();
        boolean flag = false;

        for (int i : input) {
            if (mySet.contains(i)) {
                flag = true;
            }
            mySet.add(i);
        }
        return flag;
    }
}
