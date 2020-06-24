package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Remove-Element-8d5064a1e658403ba88d87193fd8d2aa
 */

public class RemoveElement {
    /**
     * Big-O: O(n)
     * Big-O for Space Complexity: O(1) -> The algorithm is in-place
     * Justification: Iteration through the array once with two pointers
     */
    public int removeElement(int[] input, int element) {
        int i = 0;
        for (int j = 0; j < input.length; j++) {
            if (input[j] != element) {
                input[i] = input[j];
                i++;
            }
        }
        return i;
    }
}

