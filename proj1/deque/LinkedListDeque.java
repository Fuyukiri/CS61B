package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {
    private class ListNode {
        private T val;
        private ListNode next;
        private ListNode prev;

        ListNode() {
            next = null;
            prev = null;
        }

        ListNode(T val) {
            this.val = val;
            next = null;
            prev = null;
        }

    }

    private final ListNode sentinel;

    private int size;

    public LinkedListDeque() {
        sentinel = new ListNode();
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        // DLL is empty
        ListNode newHead = new ListNode(item);
        if (isEmpty()) {
            newHead.next = sentinel;
            sentinel.prev = newHead;
        } else {
            ListNode prevHead = sentinel.next;
            prevHead.prev = newHead;
            newHead.next = prevHead;
        }
        newHead.prev = sentinel;
        sentinel.next = newHead;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (isEmpty()) {
            addFirst(item);
        } else {
            ListNode newLast = new ListNode(item);
            ListNode prevLast = sentinel.prev;
            prevLast.next = newLast;
            newLast.prev = prevLast;
            newLast.next = sentinel;
            sentinel.prev = newLast;
            size++;
        }
    }

    /**
     * @return Returns the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        ListNode head = sentinel.next;
        while (head != sentinel) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     *
     * @return If no such item exists, returns null.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        ListNode ret = sentinel.next;
        ListNode newFirst = sentinel.next.next;
        sentinel.next = newFirst;
        newFirst.prev = sentinel;
        ret.next = null;
        ret.prev = null;
        size--;
        return ret.val;
    }

    /**
     * Removes the last item returns the item at the back of the deque.
     *
     * @return If no such item exists, returns null
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        ListNode ret = sentinel.prev;
        ListNode newLast = sentinel.prev.prev;
        sentinel.prev = newLast;
        newLast.next = sentinel;
        ret.next = null;
        ret.prev = null;
        size--;
        return ret.val;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        ListNode ret = sentinel.next;
        int i = 0;
        while (i != index) {
            ret = ret.next;
            i++;
        }
        return ret.val;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getRecursive(index, 0, sentinel.next);
    }

    private T getRecursive(int index, int current, ListNode head) {
        if (current == index) {
            return head.val;
        }
        return getRecursive(index, current + 1, head.next);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque<?>)) {
            return false;
        }
        if (((Deque<?>) o).size() != size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!((Deque<?>) o).get(i).equals(this.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private ListNode cur;
        private int wizPos;
        public LinkedListDequeIterator() {
            cur = sentinel;
            wizPos = 0;
        }
        @Override
        public boolean hasNext() {
            return wizPos < size;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            T returnItem = cur.next.val;
            cur = cur.next;
            wizPos++;
            return returnItem;
        }
    }
}
