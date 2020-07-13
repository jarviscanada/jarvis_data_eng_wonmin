package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

/**
 * Ticket: https://www.notion.so/Implement-Queue-using-Stacks-8f0dad6e764a4368aea09603d368a468
 */

public class QueueFromStack {
    private Stack<Integer> myStack = new Stack();
    private Stack<Integer> otherStack = new Stack();
    private int firstElement;

    public void quickPush(int element) {
        if (myStack.isEmpty()) {
            firstElement = element;
        }
        myStack.push(element);
    }

    public void slowPush(int element) {
        if (myStack.isEmpty()) {
            firstElement = element;
        }
        while(!myStack.isEmpty()) {
            otherStack.push(myStack.pop());
        }
        otherStack.push(element);
        myStack = otherStack;
    }

    public int quickPop() {
        myStack.pop();
        if (!myStack.empty()) {
            firstElement = myStack.peek();
        }
        return firstElement;
    }

    public int slowPop() {
        if (otherStack.isEmpty()) {
            while (!myStack.isEmpty()) {
                otherStack.add(myStack.pop());
            }
        }
        return otherStack.pop();
    }

    public int peek() {
        return firstElement;
    }

    public boolean isEmpty() {
        return myStack.isEmpty();
    }

}
