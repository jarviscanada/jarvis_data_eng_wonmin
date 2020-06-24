package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Sample-Check-if-a-number-is-even-or-odd-1263d0b3520842e0b51a748875f69ba8
 */

public class OddEven {
    /**
     * Big-O: O(1)
     * Justification: Simple arithmetic operation
     */
    public String evenOddMod(int input) {
        if (input % 2 == 0) {
            return "Even";
        } else return "Odd";
    }

    /**
     * Big-O: O(1)
     * Justification: Simple arithmetic operation
     * Note to Self: If the last bit of the binary value of the input is set to 1, it will be odd
     * Therefore, if we use the & operator against '1', the result will be 0 if the number is even, 1 of it is odd
     */
    public String evenOddBit(int input) {
        if ((input & 1) == 0) {
            return "Even";
        }
        else return "Odd";
    }
}


