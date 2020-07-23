package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Merge-Sorted-Array-1a4b8ebb71fb42b0b33fa3f0cd5550c4
 */

public class MergeArray {

    /**
     * Big-O: O(nlog(n))
     * Justification:
     * Credit: https://leetcode.com/problems/merge-sorted-array/discuss/29704/My-clean-java-solution
     */
    public void merge (int[] firstArray, int m, int[] secondArray, int n) {
        int firstIndex = m - 1;
        int secondIndex = n - 1;
        int finalIndex = m + n - 1;

        while (firstIndex >= 0 && secondIndex >= 0) {
            firstArray[finalIndex--] = (firstArray[firstIndex] > secondArray[secondIndex] ?
                    firstArray[firstIndex--] : secondArray[secondIndex--]);
        }

        while (secondIndex >= 0) {
            firstArray[finalIndex--] = secondArray[secondIndex--];
        }
    }
}
