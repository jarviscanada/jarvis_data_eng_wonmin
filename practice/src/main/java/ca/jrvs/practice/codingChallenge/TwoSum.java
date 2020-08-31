package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ticket: https://www.notion.so/Two-Sum-0d71382c17c74a339565cd8fa5073582
 */

public class TwoSum {

    /**
     * Big-O: O(n^2)
     * Justification: Two for-loops
     */
    public int[] bruteForce(int[] input, int target) {
        int[] sum = new int[2];

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                if (input[i] + input[j] == target && i != j) {
                    sum[0] = i;
                    sum[1] = j;
                    return sum;
                }
            }
        }

        return sum;
    }

    /**
     * Big-O: O(nlog(n))
     * Justification: Complexity of binary search is O(nlog(n)) and is nested within a for-loop
     */
    public int[] useSort(int[] input, int target) {
        int firstNum = 0, secondNum = 0;
        int[] sum = new int[2];
        int[] copiedArray = Arrays.copyOf(input, input.length);
        Arrays.sort(copiedArray);

        for (int i = 0; i < input.length; i++) {
            int ind = Arrays.binarySearch(copiedArray, target - copiedArray[i]);
            if (ind > 0 && ind != i) {
                firstNum = copiedArray[i];
                secondNum = copiedArray[ind];
                break;
            }
        }

        for (int i = 0; i < input.length; i++) {
            if (input[i] == firstNum) {
                sum[0] = i;
                break;
            }
        }

        for (int i = 0; i < input.length; i++) {
            if (input[i] == secondNum) {
                sum[1] = i;
                break;
            }
        }

        if (sum[0] > sum[1]) {
            int tmp = sum[0];
            sum[0] = sum[1];
            sum[1] = tmp;
        }

        return sum;
    }

    /**
     * Big-O: O(n)
     * Justification: Traverses through the map once to find combination
     */
    public int[] useMap(int[] input, int target) {
        Map<Integer, Integer> myMap = new HashMap();
        int[] sum = new int[2];
        for (int i = 0; i < input.length; i++) {
            myMap.put(input[i], i);
        }

        for (int i = 0; i < input.length; i++) {
            int complement = target - input[i];
            if (myMap.containsKey(complement) && myMap.get(complement) != i) {
                sum[0] = i;
                sum[1] = myMap.get(complement);
                break;
            }
        }

        return sum;
    }
}

