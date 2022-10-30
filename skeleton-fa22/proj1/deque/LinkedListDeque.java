package deque;

public class LinkedListDeque<T> {
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

        ListNode(T val, ListNode next) {
            this.val = val;
            this.next = next;
            next.prev = this;
        }
    }

    private ListNode sentinel;

    private int size;

    public LinkedListDeque() {
        sentinel = new ListNode();
        size = 0;
    }

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
     * @return Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        ListNode head = sentinel.next;
        while(head != sentinel) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * @return If no such item exists, returns null.
     */
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
     * @return  If no such item exists, returns null
     */
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
}
