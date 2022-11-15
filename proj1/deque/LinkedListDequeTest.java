package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


/**
 * Performs some basic linked list tests.
 */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

        System.out.println("Printing out deque: ");
        lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);

    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<>();
        LinkedListDeque<Double> lld2 = new LinkedListDeque<>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();

    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertNull("Should return null when removeFirst is called on an empty Deque,", lld1.removeFirst());
        assertNull("Should return null when removeLast is called on an empty Deque,", lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    public void randomizedTest() {
        LinkedList<Integer> expected = new LinkedList<>();
        LinkedListDeque<Integer> actual = new LinkedListDeque<>();

        int N = 114514;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            int randVal;
            switch (operationNumber) {
                case 0:
                    //addLast
                    randVal = StdRandom.uniform(0, 100);
                    expected.addLast(randVal);
                    actual.addLast(randVal);
                    break;

                case 1:
                    if (expected.size() > 1 && actual.size() > 1) {
                        assertEquals(expected.removeLast(), actual.removeLast());
                    }
                    break;
                case 2:
                    assertEquals(expected.size(), actual.size());
                    break;
                case 3:
                    randVal = StdRandom.uniform(0, 100);
                    expected.addFirst(randVal);
                    actual.addFirst(randVal);
                    break;
                case 4:
                    if (expected.size() > 1 && actual.size() > 1) {
                        assertEquals(expected.removeFirst(), actual.removeFirst());
                    }
                    break;
                case 5:
                    if (expected.size() > 1 && actual.size() > 1) {
                        assertEquals(expected.getLast(), actual.getRecursive(actual.size() - 1));
                        assertEquals(expected.getLast(), actual.get(actual.size() - 1));
                        assertEquals(expected.getFirst(), actual.getRecursive(0));
                        assertEquals(expected.getFirst(), actual.get(0));
                    }
                    break;
            }
        }
    }

    @Test
    public void testEquals() {
        LinkedListDeque<Integer> ll1 = new LinkedListDeque<>();
        LinkedListDeque<Integer> ll2 = new LinkedListDeque<>();
        ll1.addFirst(10);
        ll2.addFirst(10);
        assertEquals(ll1, ll2);

        ll1.removeFirst();
        assertNotEquals(ll1, ll2);

        ll1.addFirst(10);
        assertEquals(ll1, ll2);
    }

    @Test
    public void testIterator() {
        LinkedListDeque<Integer> ll1 = new LinkedListDeque<>();
        Iterator<Integer> lseer = ll1.iterator();
        assertFalse(lseer.hasNext());

        ll1.addLast(11);
        ll1.addFirst(11);
        while (lseer.hasNext()) {
            assertEquals(Integer.valueOf(11), lseer.next());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorException() {
        LinkedListDeque<Integer> ll1 = new LinkedListDeque<>();
        Iterator<Integer> lseer = ll1.iterator();
        lseer.next();
    }
}
