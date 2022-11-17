package gh2;

import deque.ArrayDeque;
import deque.Deque;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
//    private static final double CONCERT_A = 440.0;
//    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
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
//        Map<Character, Integer> keys = new HashMap<>();
//        GuitarString stringA = new GuitarString(CONCERT_A);
//        GuitarString stringC = new GuitarString(CONCERT_C);

        for (int i = 0; i < numberOfKeys; i++) {
            strings.addLast(new GuitarString(CONCERT.get(i)));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            int index = 17;
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                index = keyboard.indexOf(key);
                if (index != -1) {
                    strings.get(index).pluck();
                }
            }

            /* compute the superposition of samples */
            int next = (index + 5) % numberOfKeys;
            double sample = strings.get(index).sample() + strings.get(next).sample();
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            strings.get(index).tic();
            strings.get(next).tic();
//            double sample = stringA.sample() + stringC.sample();
//            /* play the sample on standard audio */
//            StdAudio.play(sample);
//
//            /* advance the simulation of each guitar string by one step */
//            stringA.tic();
//            stringC.tic();

        }
    }
}
