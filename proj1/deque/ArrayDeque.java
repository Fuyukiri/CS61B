package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Iterable<T>, Deque<T>{
    private T[] array;
    private int size;
    private int head;
    private int tail;

    public ArrayDeque() {
        array =(T[]) new Object[8];
        size = 0;
        tail = 0; // current tail, need to be filled
        head = 8; // current head, need move forward to be filled
    }

    @Override
    public void addFirst(T item) {
        if (size < array.length) {
            if (head == 0) {
                head = array.length;
            }
            head--;
            array[head] = item;
            size++;
        } else {
            resize();
            addFirst(item);
        }
    }

    @Override
    public void addLast(T item) {
        resize();
        if (tail == array.length) {
            tail = 0;
        }
        array[tail] = item;
        size++;
        tail++;

    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (head == array.length) {
            head = 0;
        }
        T ret = array[head];
        array[head] = null;
        head++;
        size--;
        resize();

        return ret;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (tail == 0) {
            tail = array.length;
        }
        tail--;
        T ret = array[tail];
        array[tail] = null;
        size--;
        resize();

        return ret;
    }
    private void resize() {
        // cut down size
        if (array.length / 4 > size && array.length >= 16) {
            T[] newArray = (T[]) new Object[array.length / 2];

            // if head is at the left of tail
            if (head <= tail) {
                System.arraycopy(array, head, newArray, 0, size);
            }
            // if head is at the right of tail
            // means we have two separate parts of array
            else {
                System.arraycopy(array, head, newArray, 0, array.length - head);
                System.arraycopy(array, 0, newArray, array.length - head, tail);
            }
            array = newArray;
            head = array.length;
            tail = size;
        }

        // increase size
        if (array.length == size) {
            T[] newArray = (T[]) new Object[size * 2];
            // copy last part
            System.arraycopy(array, head, newArray, 0, size - head);
            // copy first part
            System.arraycopy(array, 0, newArray, size - head, tail);
            array = newArray;
            head = array.length;
            tail = size;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
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
            if (((Deque<?>) o).get(i) != get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (isEmpty() || index >= size) {
            return null;
        }
        if (head == array.length) {
            return array[index];
        }
        if (head < tail) {
            return array[index + head];
        }
        int rightPart = array.length - head;
        if (rightPart > index) {
            return array[head + index];
        }
        return array[index - rightPart];
    }


    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {

        private int wizPos;
        public ArrayDequeIterator() {
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
            T returnItem = get(wizPos);
            wizPos++;
            return returnItem;
        }
    }
}
