package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ticket: https://www.notion.so/Valid-Anagram-c7d3c9fea82c449db85e25cbc9d227d8
 */

public class ValidAnagram {

    /**
     * Big-O: O(nlog(n))
     * Justification: The time complexity for Arrays.sort() is nlog(n)
     */
    public boolean useSort(String s, String t) {
        boolean flag = true;
        char[] inputArray = s.toCharArray();
        char[] comparisonArray = t.toCharArray();
        Arrays.sort(inputArray);
        Arrays.sort(comparisonArray);

        if (inputArray.length != comparisonArray.length) {
            flag = false;
        }

        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i] != comparisonArray[i]) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    /**
     * Big-O: O(n)
     * Justification: Loops through the input String once to store in map and
     *                loops through the comparison String once to compare
     */
    public boolean useMap(String s, String t) {
        boolean flag = true;
        char[] inputArray = s.toCharArray();
        char[] comparisonArray = t.toCharArray();
        Map<Character, Integer> myMap = new HashMap<>();

        if (inputArray.length != comparisonArray.length) {
            flag = false;
        }

        for (int i = 0; i < inputArray.length; i++) {
            myMap.put(inputArray[i], i);
        }

        for (int i = 0; i < comparisonArray.length; i++) {
            if (!myMap.containsKey(comparisonArray[i])) {
                flag = false;
                break;
            }
        }

        return flag;
    }
}

