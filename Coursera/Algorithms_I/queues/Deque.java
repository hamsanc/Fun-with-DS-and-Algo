// *****************************************************************************

//*  Name:

//*  Date:

//*  Description: Data Structure used is double linked list

//* citation : this java code is taken from the Algo part 1 book

//* DoublyLinkedList.java.

//****************************************************************************

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    /* Instance variable */
    private int n = 0; // number elements in the list
    private Node pre;
    private Node post;

    // Construct an empty deque
    public Deque() {
        pre = new Node();
        post = new Node();
        pre.next = post;
        post.prev = pre;

    }

    // is the deque empty ?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items in deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) throw new IllegalArgumentException();
        Node x = pre.next;
        Node y = new Node();
        y.item = item;
        y.next = x;
        x.prev = y;
        y.prev = pre;
        pre.next = y;
        n++;

    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) throw new IllegalArgumentException();
        Node x = post.prev;
        Node y = new Node();
        y.item = item;
        y.next = post;
        post.prev = y;
        y.prev = x;
        x.next = y;
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Node x = pre.next;
        Node y = x.next;
        Item item = x.item;
        pre.next = y;
        y.prev = pre;
        n--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Node x = post.prev;
        Node y = x.prev;
        Item item = x.item;
        post.prev = y;
        y.next = post;
        n--;
        return item;
    }

    // returns an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = pre.next;
        private int index = 0;
        private int size = n;

        public boolean hasNext() {
            return index < size;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            index++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();

        }
    }


    private class Node {
        Item item;
        Node next;
        Node prev;
    }


    public static void main(String[] args)  {

        Deque<Integer> deque = new Deque<>();

        System.out.println(deque.isEmpty());
        for (int i = 1; i < 20; i += 2) {

            deque.addFirst(i);
            deque.addLast(i + 1);
        }

        System.out.println(deque.size());
        System.out.println(deque.isEmpty());

        Iterator<Integer> iterator = deque.iterator();


        while(iterator.hasNext()) {
            System.out.print(iterator.next()+" ");
        }

        System.out.println();

        for (int i = 1; i <= 10; i ++) {

            System.out.print(deque.removeFirst()+" ");

        }

        System.out.println();
        System.out.println(deque.size());
        System.out.println(deque.isEmpty());

        for (int i = 1; i <= 10; i ++) {

            System.out.print(deque.removeLast()+" ");

        }

        //deque.removeLast(); // to test exception in remove
        System.out.println();
        System.out.println(deque.size());
        System.out.println(deque.isEmpty());

        for (int i = 100; i < 120; i += 2) {

            deque.addFirst(i);
            deque.addFirst(i + 1);
            deque.removeLast();

        }
        Iterator<Integer> iterator1 = deque.iterator();

        while(iterator1.hasNext()) {
            System.out.print(iterator1.next()+" ");
        }
        System.out.println();
        System.out.println(deque.size());
        System.out.println(deque.isEmpty());

        //  iterator.remove();
    }

}
