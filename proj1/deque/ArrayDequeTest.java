package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Before;
import org.junit.Test;

import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    private ArrayDeque<Integer> ad1;
    private int counter;

    @Before
    public void setup() {
        ad1 = new ArrayDeque<>();
        counter = 0;
    }

    @Test
    public void addIsEmptySizeTest() {

        ArrayDeque<String> ad1 = new ArrayDeque<>();
        assertTrue("A newly initialized LLDeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");

        assertEquals(1, ad1.size());
        assertFalse("lld1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        // should be empty
        assertTrue("lld1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", ad1.isEmpty());

    }

    @Test
    public void addFirst_removeLast() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 4; i++) {
            ad1.addFirst(10);
        }
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        assertEquals(10, (int)ad1.get(0));
        assertEquals(1, ad1.size());

        ad1.printDeque();
    }

    @Test
    public void addFirst_removeLast_resize() {
        for (int i = 0; i < 10; i++) {
            ad1.addLast(i);
        }

        for (int i = 0; i < 20; i++) {
            ad1.addFirst(i);
        }

        for (int i = 0; i < 20; i++) {
            ad1.removeLast();
        }

        for (int i = 0; i < 5; i++) {
            ad1.removeLast();
        }
    }

    @Test
    public void addFirst_resize2() {

        for (int i = 0; i < 8; i++) {
            ad1.addFirst(counter++);
        }

        ad1.addLast(counter++);

        ad1.printDeque();
    }

    @Test
    public void getTest() {
        ad1.addLast(counter++);
        assertEquals(0, (int) ad1.get(0));
        ad1.addFirst(counter++);
        assertEquals(1, (int) ad1.get(0));
    }


    @Test
    public void addLast_resize() {
        for (int i = 0; i < 8; i++) {
            ad1.addLast(counter++);
        }
        ad1.removeFirst();

        ad1.addLast(counter++);
        ad1.addFirst(counter++);

        ad1.printDeque();
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigDequeTest() {
        for (int i = 0; i < 1000000; i++) {
            ad1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) ad1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) ad1.removeLast(), 0.0);
        }

    }

    @Test
    public void randomizedTest() {
        Deque<Integer> expected = new java.util.ArrayDeque<>();

        int N = 1919810;
        int randVal;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            switch (operationNumber) {
                case 0:
                    randVal = StdRandom.uniform(0, 100);
                    expected.addLast(randVal);
                    ad1.addLast(randVal);
                    break;
                case 1:
                    if (expected.size() > 1) {
                        Integer expect = expected.removeLast();
                        Integer actual = ad1.removeLast();
                        assertEquals(expect, actual);
                    }
                    break;
                case 2:
                    assertEquals(expected.size(), ad1.size());
                    break;
                case 3:
                    if (expected.size() > 1) {
                        Integer expect = expected.removeFirst();
                        Integer actual = ad1.removeFirst();
                        assertEquals(expect, actual);
                    }
                    break;
                case 4:
                    randVal = StdRandom.uniform(0, 100);
                    expected.addFirst(randVal);
                    ad1.addFirst(randVal);
                    break;
                case 5:
                    if (expected.size() > 1) {
                        assertEquals(expected.getLast(), ad1.get(ad1.size() - 1));
                        assertEquals(expected.getFirst(), ad1.get(0));
                        break;
                    }
            }
        }
        Iterator<Integer> expectedAdseer = expected.iterator();

        for (Integer number : ad1) {
            assertEquals(expectedAdseer.next(), number);
        }
    }

    @Test
    public void testEquals() {
        ArrayDeque<Integer> ad2 = new ArrayDeque<>();
        ad1.addFirst(10);
        ad2.addFirst(10);
        assertEquals(ad1, ad2);

        ad1.removeFirst();
        assertNotEquals(ad1, ad2);

        ad1.addFirst(10);
        assertEquals(ad1, ad2);
    }

    @Test
    public void testIteratorSimple() {
        Iterator<Integer> adseer = ad1.iterator();
        assertFalse(adseer.hasNext());

        ad1.addLast(11);
        ad1.addFirst(11);
        while (adseer.hasNext()) {
            assertEquals(Integer.valueOf(11), adseer.next());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testIteratorException() {
        Iterator<Integer> adseer = ad1.iterator();
        adseer.next();
    }

    @Test
    public void randomIteratorTest() {
        Deque<Integer> expected = new java.util.ArrayDeque<>();
        int N = 114514;
        int randVal;
        for (int i = 0; i < N; i++) {
            randVal = StdRandom.uniform(0, 100);
            expected.addFirst(randVal);
            ad1.addFirst(randVal);
        }
        Iterator<Integer> adseer = ad1.iterator();
        Iterator<Integer> expectedAdseer = expected.iterator();

        while (adseer.hasNext()) {
            assertEquals(expectedAdseer.next(), adseer.next());
        }
    }
}
