package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackFromQueueTest {

    @Test
    public void testQueue() {
        StackFromQueue myStack = new StackFromQueue();
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);

        assertEquals(4, myStack.top());
        assertEquals(4, myStack.pop());
        assertEquals(3, myStack.pop());
        assertEquals(2, myStack.pop());

        assertEquals(true, myStack.isEmpty());
    }

    @Test
    public void testTwoQueue() {
        StackFromQueue myStack = new StackFromQueue();
        myStack.twoQueuePush(2);
        myStack.twoQueuePush(3);
        myStack.twoQueuePush(4);

        assertEquals(4, myStack.twoQueuePop());
        assertEquals(3, myStack.twoQueuePop());
        assertEquals(2, myStack.twoQueuePop());

        assertEquals(true, myStack.isEmpty());
    }

}