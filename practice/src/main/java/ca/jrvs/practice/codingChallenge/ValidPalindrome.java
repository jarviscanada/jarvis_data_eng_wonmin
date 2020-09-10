package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Valid-Palindrome-965cd0b8f7484fefa157bd70aa387867
 */

public class ValidPalindrome {

    /**
     * Big-O: O(n)
     * Justification: We parse the entire string from both ways
     */
    public boolean useTwoPointer(String input) {
        int low = 0, high = input.length() - 1;

        while (low < high) {
            if (input.charAt(low) != input.charAt(high)) {
                return false;
            }
            low++;
            high--;
        }
        return true;
    }

    /**
     * Big-O: O(n)
     * Justification: We still parse through the entirety of the String from both ends
     */
    public boolean useRecursion(String input) {
        if (input.length() == 0 || input.length() == 1) {
            return true;
        }
        if (input.charAt(0) == input.charAt(input.length() - 1)) {
            return useRecursion(input.substring(1, input.length() - 1));
        }
        return false;
    }
}
