package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Rotate-String-f81eda8e6d984dcdbebbbc4fa6d494ce
 */

public class RotateString {

    /**
     * Big-O: O(N^2)
     * Justification: Since we doubled the length of A
     * Note: All rotations of A are contained in A+A -> Check if B is a substring of A+A
     */
    public boolean rotate(String A, String B) {
        return A.length() == B.length() && (A+A).contains(B);
    }
}
