/* *****************************************************************************
 *  Name: Randomized queue
 *  Date: 8/5/2023
 *  Description: A randomized queue is similar to a stack or queue,
 *               except that the item removed is chosen uniformly at random
 *               among items in the data structure.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Cannot enqueue null item");
        if (size == items.length) resize(2 * items.length);
        items[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("RandomizedQueue is empty");
        int randomIndex = StdRandom.uniformInt(size);
        Item item = items[randomIndex];
        items[randomIndex] = items[size - 1];
        items[size - 1] = null;
        size--;
        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("RandomizedQueue is empty");
        int randomIndex = StdRandom.uniformInt(size);
        return items[randomIndex];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int currentIndex = 0;
        private final Item[] shuffledItems;

        public RandomizedQueueIterator() {
            shuffledItems = (Item[]) new Object[size];
            System.arraycopy(items, 0, shuffledItems, 0, size);
            StdRandom.shuffle(shuffledItems);
        }

        public boolean hasNext() {
            return currentIndex < size;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No more items to return");
            return shuffledItems[currentIndex++];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    private void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
    }

    public static void main(String[] args) {
        // Test your RandomizedQueue implementation here
    }
}
