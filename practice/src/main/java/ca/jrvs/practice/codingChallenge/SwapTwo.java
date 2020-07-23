package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Swap-two-numbers-6650436ebd6649baa2cbc7dc37025c07
 */

public class SwapTwo {
    /**
     * Big-O: O(1)
     * Justification: Simple swapping operation with only an array of size 2
     */
    public int[] swapTwoArith(int[] input) {
        input[0] = input[0] + input[1];
        input[1] = input[0] - input[1];
        input[0] = input[0] - input[1];
        return input;
    }

    /**
     * Big-O: O(1)
     * Justification: Simple swapping operation with only an array of size 2
     */
    public int[] swapTwoBit(int[] input) {
        input[0] = input[0] ^ input[1];
        input[1] = input[0] ^ input[1];
        input[0] = input[0] ^ input[1];
        return input;
    }
}
