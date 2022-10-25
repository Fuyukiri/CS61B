package timingtest;

import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        int N;
        final int M = 10000;
        int muliplier = 1;
        AList<Integer> Ns = new AList<>();
        AList<Double> times = new AList<>();
        AList<Integer> opCounts = new AList<>();
        for (int i = 0; i < 8; i++) {
            N = 1000 * muliplier;
            SLList<Integer> current = new SLList<>();
            for (int j = 0; j < N; j++) {
                current.addLast(0);
            }
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < M; j++) {
                current.getLast();
            }
            times.addLast(sw.elapsedTime());
            Ns.addLast(N);
            opCounts.addLast(M);
            muliplier *= 2;
        }
        printTimingTable(Ns, times, opCounts);
    }

}
