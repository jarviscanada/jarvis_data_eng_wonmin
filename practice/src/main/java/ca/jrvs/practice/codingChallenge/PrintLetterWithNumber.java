package ca.jrvs.practice.codingChallenge;
import java.util.*;

/**
 * Ticket: https://www.notion.so/Print-letter-with-number-d84b9f15a3974ea28855807d8589cfa8
 */

public class PrintLetterWithNumber {

    public String print(String input) {
        char[] charArray = input.toCharArray();
        char[] charValues = new char[charArray.length];
        char[] newArray = new char[charArray.length * 2];
        Map<Character, Integer> myMap = new LinkedHashMap<>();

        for (int i = 0; i < charArray.length; i++) {
            myMap.put(charArray[i], ((int) charArray[i]));
        }

        Integer[] intValues = myMap.values().toArray(new Integer[charArray.length]);
        for (int i = 0; i < intValues.length; i++) {
            if (intValues[i] >= 97) {
                intValues[i] = intValues[i] - 96;
            }
            else if (intValues[i] >= 65 && intValues[i] <= 90) {
                intValues[i] = intValues[i] - 38;
            }
            charValues[i] = (char)intValues[i].intValue();
        }

        int index = 0;
        for (int i = 0; i < charArray.length; i++) {
            newArray[index++] = charArray[i];
            newArray[index++] = charValues[i];
        }

        String combination = String.valueOf(newArray);
        return combination;
    }
}

