import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    /**
     * Create a new  Deque object.
     */
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        System.out.println(deque.removeFirst()) ;
    }


    /**
     * Check whether the Deque is empty.
     * @return whether the Deque is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Return the number of the item in this Deque.
     * @return the number of the item in this Deque
     */
    public int size() {
        return this.size;
    }

    /**
     *
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == 0) {
            this.first = new Node(item, null, null);
            this.last = this.first;
        } else {
            this.first = new Node(item, null, this.first);
            this.first.next.previous = this.first;
        }
        this.size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (this.size == 0) {
            this.first = new Node(item, null, null);
            this.last = this.first;
        } else {
            this.last = new Node(item, this.last, null);
            this.last.previous.next = this.last;
        }
        this.size++;
    }

    public Item removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        } else {
            Item oldFirst = this.first.item;
            this.first = this.first.next;
            this.size--;
            if (this.size == 0) {
                this.last = null;
            }
            else {
                this.first.previous = null;
            }
            return oldFirst;
        }
    }

    public Item removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        } else {
            Item oldLast = this.last.item;
            this.last = this.last.previous;
            this.size--;
            if (this.size == 0) {
                this.first = null;
            }
            else {
                this.last.next = null;
            }
            return oldLast;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        class DequeIterator implements Iterator<Item> {
            private Node current;

            private DequeIterator() {
                this.current = first;
            }

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public Item next() {
                if (this.current == null) {
                    throw new NoSuchElementException();
                }
                Item item = this.current.item;
                this.current = this.current.next;
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        return new DequeIterator();
    }

    private class Node {
        private Item item;
        private Node previous;
        private Node next;

        private Node(Item item, Node previous, Node next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }


}
