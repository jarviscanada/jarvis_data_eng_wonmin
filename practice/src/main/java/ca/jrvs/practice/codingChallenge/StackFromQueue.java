package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Ticket: https://www.notion.so/Implement-Stack-using-Queue-41b2e1e5d6a643ea97f2f096e28df189
 */

public class StackFromQueue {
    private Queue myQueue = new LinkedList();
    private Queue otherQueue = new LinkedList();
    private int topElement = 0;


    public void push(int element) {
        myQueue.add(element);
    }

    public void twoQueuePush(int element) {
        otherQueue.add(element);
        topElement = element;
        while(!myQueue.isEmpty()) {
            otherQueue.add(myQueue.remove());
        }
        Queue tempQueue = myQueue;
        myQueue = otherQueue;
        otherQueue = tempQueue;
    }

    public int pop() {
        int topElement = top();
        myQueue.remove(topElement);
        return topElement;
    }

    public int twoQueuePop() {
        int topElement = 0;

        while (otherQueue.size() > 1) {
            topElement = (int) otherQueue.remove();
            myQueue.add(topElement);
        }
        topElement = (int) myQueue.remove();
        Queue tempQueue = myQueue;
        myQueue = otherQueue;
        otherQueue = tempQueue;
        return topElement;
    }

    public int top() {
        int topElement = 0;
        for (Object i : myQueue) {
            topElement = (int) i;
        }
        return topElement;
    }

    public boolean isEmpty() {
        return myQueue.isEmpty();
    }

}

