import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Object[] container;
    private int size;


    /**
     * Create a new RandomizedQueue object.
     */
    public RandomizedQueue() {
        this.container = new Object[1];
        this.size = 0;
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 100; i++) {
            rq.enqueue(i);
        }
        int index = 0;
        for (int i: rq) {
            System.out.println(index);
            index++;
        }
    }

    /**
     * Check whether the RandomizedQueue is empty.
     *
     * @return whethere the RandomizedQueue is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;

    }

    /**
     * Return the number of the itemsToIterate in this RandomizedQueue
     *
     * @return the size of this RandomizedQueue
     */
    public int size() {
        return this.size;

    }

    /**
     * Add the item into this RandomizedQueue
     *
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == this.container.length) {
            Object[] newContainer = new Object[2 * this.size];
            for (int i = 0; i < this.size; i++) {
                newContainer[i] = this.container[i];
            }
            this.container = newContainer;
        }
        this.container[this.size] = item;
        this.size++;
    }

    /**
     * Remove and return a random item
     *
     * @return a removed random item
     */
    public Item dequeue() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(this.size);
        Object item = this.container[index];
        this.container[index] = this.container[this.size - 1];
        this.container[this.size - 1] = null;
        this.size--;
        if (this.size <= 0.25 * this.container.length) {
            Object[] newContainer;
            if (this.size == 0) {
                newContainer = new Object[1];
            }
            else {
                newContainer = new Object[this.size];
            }
            for (int i = 0; i < this.size; i++) {
                if (this.container[i] != null) {
                    newContainer[i] = this.container[i];
                }
            }
            this.container = newContainer;
        }
        return (Item) item;
    }

    /**
     * Return a random item without removing it.
     *
     * @return a random item
     */
    public Item sample() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(this.size);
        Object item = this.container[index];
        return (Item) item;
    }

    @Override
    public Iterator<Item> iterator() {
        class RandomizedQueueIterator implements Iterator<Item> {
            private Object[] itemsToIterate;
            private int numToIterate;

            public RandomizedQueueIterator() {
                this.itemsToIterate = container.clone();
                this.numToIterate = size;
            }

            @Override
            public boolean hasNext() {
                return this.numToIterate != 0;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int index = StdRandom.uniform(numToIterate);
                Object item = this.itemsToIterate[index];
                this.itemsToIterate[index] = this.itemsToIterate[this.numToIterate - 1];
                this.itemsToIterate[this.numToIterate - 1] = null;
                this.numToIterate--;
                return (Item) item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
        return new RandomizedQueueIterator();
    }
}
