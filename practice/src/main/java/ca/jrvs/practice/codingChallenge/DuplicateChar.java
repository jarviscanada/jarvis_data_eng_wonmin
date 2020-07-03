package ca.jrvs.practice.codingChallenge;

import java.util.*;

/**
 * Ticket: https://www.notion.so/Duplicate-Characters-9fa1d0cfa7974bfcbe53cb4476dde3e7
 */

public class DuplicateChar {

    /**
     * Big-O: O(n)
     * Justification: The time complexity depends on the length of the input string
     */
    public List<Character> duplicateChar(String input) {

        List<Character> dupList = new ArrayList<>();
        char[] charArray = input.toCharArray();
        Map<Character, Integer> myMap = new HashMap();

        for (char c:charArray) {
            if (myMap.containsKey(c)) {
                myMap.put(c, myMap.get(c) + 1);
            } else {
                myMap.put(c,1);
            }
        }

        Set<Character> keySet = myMap.keySet();

        for (char c:keySet) {
            if(myMap.get(c) > 1) {
                dupList.add(c);
            }
        }

        return dupList;
    }
}
