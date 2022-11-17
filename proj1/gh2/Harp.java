package gh2;

import deque.LinkedListDeque;

public class Harp extends GuitarString{
    private static final double DECAY = .997;
    public Harp(double frequency) {
        super(frequency * 2);
    }
    @Override
    public void tic() {
        double frontSample = buffer.removeFirst();
        double newDouble = (frontSample + buffer.get(0)) / 2 * DECAY;
        buffer.addLast(-newDouble);
    }
}
