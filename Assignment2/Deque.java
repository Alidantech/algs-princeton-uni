/* *****************************************************************************
 *  Name: Deque
 *  Date: 8/5/2023
 *  Description: A double-ended queue or deque (pronounced “deck”) is a
 *               generalization of a stack and a queue that supports adding
 *               and removing items from either the front or the back of the
 *               data structure.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null item");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (isEmpty()) last = first;
        else oldFirst.prev = first;
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot add null item");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = first.item;
        first = first.next;
        size--;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) first = null;
        else last.next = null;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items to return");
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    public static void main(String[] args) {
        Deque<String> dq = new Deque<>();

        StdOut.println(dq.isEmpty());
        StdOut.println(dq.isEmpty());

        dq.addFirst("one");
        dq.addLast("two");
        dq.addLast("three");
        dq.addLast("five");
        dq.addLast("ten");
        dq.addFirst("zero");

        StdOut.println(dq.isEmpty());
        StdOut.println(dq.size());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.removeFirst());
        StdOut.println(dq.size());

    }
}
