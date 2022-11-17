package gh2;

import deque.ArrayDeque;
import deque.Deque;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
    private static final int numberOfKeys = 37;
    private static final Deque<Double> CONCERT;

    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    static {
        CONCERT = new ArrayDeque<>();
        for (int i = 0; i < numberOfKeys; i++) {
            CONCERT.addLast(440.0 * Math.pow(2, ((double) i - 24) / 12));
        }
    }

    public static void main(String[] args) {
        Deque<GuitarString> strings = new ArrayDeque<>();

        for (int i = 0; i < numberOfKeys; i++) {
            strings.addLast(new GuitarString(CONCERT.get(i)));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1) {
                    strings.get(index).pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < numberOfKeys; i++) {
                sample += strings.get(i).sample();
                strings.get(i).tic();
            }
            StdAudio.play(sample);
        }
    }
}
