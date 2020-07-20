package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;

/**
 * Ticket: https://www.notion.so/Duplicate-LinkedList-Node-8af3045722574a7bad46bd8ef085a564
 */

public class LL_RemoveDupeNode {

    static class node {
        int val;
        node next;

        public node(int val) {
            this.val = val;
        }
    }

    /**
     * Big-O: O(n)
     * Justification: Traverses through the list once to compare and iterate the Hash table
     */
    static void removeDuplicate(node head) {
        HashSet<Integer> hs = new HashSet<>();
        node current = head;
        node prev = null;

        while (current != null) {
            int curval = current.val;

            if (hs.contains(curval)) {
                prev.next = current.next;
            } else {
                hs.add(curval);
                prev = current;
            }
            current = current.next;
        }

    }

    public static void printList(node head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }

    public static void main(String[] args) {
        node start = new node(10);
        start.next = new node(12);
        start.next.next = new node(11);
        start.next.next.next = new node(11);
        start.next.next.next.next = new node(12);
        start.next.next.next.next.next = new node(11);
        start.next.next.next.next.next.next = new node(10);

        System.out.println("Linked list before removing duplicates:");
        printList(start);

        removeDuplicate(start);

        System.out.println("\nLinked list after removing duplicates:");
        printList(start);
    }
}
