package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Check-if-a-String-contains-only-digits-727929e0649a4cdca8b2dcac8940233a
 */

public class CheckString {

    /**
     * Big-O: O(n)
     * Justification: The complexity solely depends on the length of the input String
     */
    public boolean useASCII(String input) {
        char[] charArray = input.toCharArray();
        boolean flag = true;

        for (char c:charArray) {
            int asciiVal = (int) c;
            if (asciiVal < 48 || asciiVal > 57) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Big-O: O(n)
     * Justification: The complexity solely depends on the length of the input String
     */
    public boolean useAPI(String input) {
        boolean flag = true;

        try {
            Integer.valueOf(input);
            flag = true;
        } catch (NumberFormatException e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Big-O: O(n)
     * Justification: The complexity solely depends on the length of the input String
     */
    public boolean useRegex(String input) {
        String regex = "[0-9]+";
        return (input.matches(regex));
    }

}
