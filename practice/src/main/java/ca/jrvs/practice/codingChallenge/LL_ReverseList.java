package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Reverse-Linked-List-be90ab2567944e8fbb0af92d4f5c4645
 */

public class LL_ReverseList {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static ListNode useIteration(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }

    public static ListNode useRecursion(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode pointer = useRecursion(head.next);
        head.next.next = head;
        head.next = null;
        return pointer;
    }

}

