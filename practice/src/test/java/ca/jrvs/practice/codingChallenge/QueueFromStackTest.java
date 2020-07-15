package ca.jrvs.practice.codingChallenge;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueueFromStackTest {

    @Test
    public void quickQueueTest() {
        QueueFromStack myQueue = new QueueFromStack();
        myQueue.quickPush(2);
        myQueue.quickPush(3);
        myQueue.quickPush(4);

        assertEquals(2, myQueue.peek());
        assertEquals(2, myQueue.quickPop());
        assertEquals(3, myQueue.quickPop());
        assertEquals(4, myQueue.quickPop());
        assertEquals(true, myQueue.isEmpty());
    }

    @Test
    public void slowQueueTest() {
        QueueFromStack myQueue = new QueueFromStack();
        myQueue.slowPush(2);
        myQueue.slowPush(3);
        myQueue.slowPush(4);

        assertEquals(2, myQueue.peek());
        assertEquals(2, myQueue.slowPop());
        assertEquals(3, myQueue.slowPop());
        assertEquals(4, myQueue.slowPop());
        assertEquals(true, myQueue.isEmpty());
    }

}