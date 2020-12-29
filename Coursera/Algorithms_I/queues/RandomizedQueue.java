/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rq;
    private int n;
    private int first;
    private int last;

    public RandomizedQueue() {

        rq = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    // Check if the que is empty
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of elements in the que
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = rq[i];
        }
        rq = copy;
        first = 0;
        last = n;
    }

    // add Item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == rq.length) resize(2 * rq.length);
        rq[last++] = item;
        n++;

    }


    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = null;
        if (size() > 1) {
            int k = StdRandom.uniform(0, n );
            item = rq[k];
            rq[k] = rq[last - 1];
            rq[last - 1] = null;
        }
        else {
            item = rq[last - 1];
            rq[last - 1] = null;
        }

        n--;
        last--;
        if (n > 0 && n == rq.length / 4) resize(rq.length / 2);
        return item;
    }

    public Item sample() {

        if (isEmpty()) throw new NoSuchElementException();
        Item item = null;

        if (size() > 1) {
            int k = StdRandom.uniform(0, n );
            item = rq[k];
        }
        else {
            item = rq[last - 1];
        }

        return item;

    }

    public Iterator<Item> iterator() {
        return new rqIterator();
    }

    private class rqIterator implements Iterator<Item> {
        private int current;
        private int size;
        Item[] rqcopy;


        public rqIterator() {
            current = 0;
            size = size();
            rqcopy = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                int k = StdRandom.uniform(i + 1);
                rqcopy[i] = rq[i];
                Item t = rqcopy[i];
                rqcopy[i] = rqcopy[k];
                rqcopy[k] = t;
            }
        }

        public boolean hasNext() {
            return current < size;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return rqcopy[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();

        }
    }

    /*public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }*/

    public static void main(String[] args) {

        RandomizedQueue<Integer> rqint = new RandomizedQueue<>();

        for (int i = 10; i <= 20; i++) {
            rqint.enqueue(i);
        }

        StdOut.println(rqint);

        for (int i = 10; i <= 20; i++) {
            StdOut.print(rqint.sample() + " ");
        }
        StdOut.println();

        for (int i = 10; i < 20; i++) {
            StdOut.print(rqint.dequeue() + " ");
        }

        StdOut.println();
        StdOut.print(rqint.sample() + " ");
        StdOut.println(rqint);

        for (int i = 9; i <= 18; i++) {
            rqint.enqueue(i);
        }

        StdOut.println(rqint);
        for (int i = 0; i <= 5; i++) {
            StdOut.print(rqint.dequeue() + " ");
        }

        StdOut.println();
        StdOut.println(rqint);


        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }


        Iterator<Integer> iterator1 = queue.iterator();

        while (iterator1.hasNext()) {
            System.out.print(iterator1.next() + " ");
        }
    }


}
