package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Duplicates-from-Sorted-Array-bbc69126738e47c096559e303988615e
 */

public class SortedArrayDuplicate {

    /**
     * Big-O: O(n)
     * Justification: Iterates through the array once to merge duplicates
     */
    public int removeDup(int[] input) {
        int i = 0;
        for (int j = 1; j < input.length; j++) {
            if(input[j] != input[i]) {
                i++;
                input[i] = input[j];
            }
        }
        return i + 1;
    }
}
