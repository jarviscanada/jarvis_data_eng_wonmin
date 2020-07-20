package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Ticket: https://www.notion.so/Valid-Parentheses-d20514aeec9f4f9ca7b60f401aa9e1ee
 */

public class ValidParantheses {

    public boolean isValid(String input) {
        char[] charArray = input.toCharArray();
        Stack<Character> myStack = new Stack();
        Map<Character, Character> myMap = new HashMap();

        //Initialize map with bracket pairs
        myMap.put(')', '(');
        myMap.put(']', '[');
        myMap.put('}', '{');

        for (char c:charArray) {
            if (myMap.containsKey(c)) {
                char topElement = myStack.pop();
                if (topElement != myMap.get(c)) {
                    return false;
                }
            } else {
                myStack.push(c);
            }
        }

        return myStack.isEmpty();
    }
}

