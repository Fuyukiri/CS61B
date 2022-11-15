package deque;


import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public T max() {
        int size = this.size();
        if (size == 0) {
            return null;
        }

        T max = this.get(0);

        for (int i = 1; i < size; i++) {
            T curVal = get(i);
            if (comparator.compare(max, curVal) < 0) {
                max = curVal;
            }
        }
        return max;
    }

    /**
     * @param c Comparator
     * @return returns the maximum element in the deque as governed by the parameter Comparator c.
     * If the MaxArrayDeque is empty, simply return null
     */
    public T max(Comparator<T> c) {
        int size = this.size();
        if (size == 0) {
            return null;
        }

        T max = this.get(0);

        for (int i = 1; i < size; i++) {
            T curVal = get(i);
            if (c.compare(max, curVal) < 0) {
                max = curVal;
            }
        }
        return max;
    }
}
