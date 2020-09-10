package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Nth-Node-From-End-of-LinkedList-87f2f427bc0540f4b6488fda6e6c9d87
 */
public class LL_RemoveNthNodeFromEnd {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int length  = 0;
        ListNode first = head;

        while (first != null) {
            length++;
            first = first.next;
        }

        length -= n;
        first = dummy;

        while (length > 0) {
            length--;
            first = first.next;
        }

        first.next = first.next.next;
        return dummy.next;
    }

    public static ListNode useTwoPointer(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;

        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }

        while (first != null) {
            first = first.next;
            second = second.next;
        }

        second.next = second.next.next;
        return dummy.next;
    }

    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
    public static void main(String[] args) {
        ListNode start = new ListNode(1);
        start.next = new ListNode(2);
        start.next.next = new ListNode(3);
        start.next.next.next = new ListNode(4);
        start.next.next.next.next = new ListNode(5);

        printList(start);
        //removeNthFromEnd(start, 2);
        //useTwoPointer(start, 2);
        printList(start);
    }
}
