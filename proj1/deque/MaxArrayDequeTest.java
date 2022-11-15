package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.ArrayDeque;

import static org.junit.Assert.assertEquals;

public class MaxArrayDequeTest {
    private MaxArrayDeque<Integer> maxArrayDeque;
    private Deque<Integer> expected;

    @Before
    public void setup() {
        // By default, use natural order to compare
        maxArrayDeque = new MaxArrayDeque<>(Integer::compareTo);
        expected = new ArrayDeque<>();
    }

    @Test
    public void testMax() {
        for (int i = 0; i < 114514; i++) {
            int randVal = StdRandom.uniform(0, 100);
            maxArrayDeque.addFirst(randVal);
            expected.addFirst(randVal);
            randVal = StdRandom.uniform(0, 100);
            maxArrayDeque.addLast(randVal);
            expected.addLast(randVal);
        }
        assertEquals(Collections.max(expected), maxArrayDeque.max());

        Comparator<Integer> reverseOrderComparator = Comparator.reverseOrder();

        assertEquals(Collections.max(expected, reverseOrderComparator),
                maxArrayDeque.max(reverseOrderComparator));
    }
}
