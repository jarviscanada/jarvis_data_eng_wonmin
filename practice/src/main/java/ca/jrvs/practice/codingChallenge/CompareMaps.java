package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * Ticket: https://www.notion.so/How-to-compare-two-maps-3e8caab2b0054648a2d8bcbfd556f41c
 */

public class CompareMaps {

    /**
     * Note: The way that Map.equals() works is by comparing keys and values using the Object.equals() method.
     * This means it only works when both key and value objects implement equals() properly.
     */
    public static boolean useEquals(Map firstMap, Map secondMap) {
        return firstMap.equals(secondMap);
    }

    public static void main(String[] args) {
        Map firstMap = new HashMap();
        Map secondMap = new HashMap();
        Map thirdMap = new HashMap();

        firstMap.put(27, "A");
        firstMap.put(1, "a");
        firstMap.put(28, "B");

        secondMap.put(27, "A");
        secondMap.put(1, "a");
        secondMap.put(28, "B");

        thirdMap.put(27, "A");
        thirdMap.put(1, "a");
        thirdMap.put(26, "z");

        System.out.println(useEquals(firstMap, secondMap));
        System.out.println(useEquals(firstMap, thirdMap));
    }

}

