package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/String-to-Integer-atoi-4acc872bae024c7fa2226fdf6e3772a2
 */

public class StrToInt {

    /**
     * Big-O: O(n)
     * Justification: Time complexity depends on the length of the string
     */
    public int withParse(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e){
            throw new NumberFormatException("Invalid Format");
        }
    }

    /**
     * Big-O: O(n)
     * Justification: Time complexity depends on the length of the string
     * Note to self:
     */
    public int pureConversion(String input) {
        char[] charArr = input.toCharArray();
        int sum = 0;
        int zeroASCII = (int) '0';

        for (char c:charArr) {
            int tmpASCII = (int)c;
            sum = (sum * 10) + (tmpASCII - zeroASCII);
        }
        return sum;
    }
}