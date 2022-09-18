package bearmaps.proj2c.integerhoppuzzle;

import bearmaps.proj2c.LazySolver;
import bearmaps.proj2c.ShortestPathsSolver;
import bearmaps.proj2c.SolutionPrinter;

/**
 * Showcases how the AStarSolver can be used for solving integer hop puzzles.
 * NOTE: YOU MUST REPLACE LazySolver WITH AStarSolver OR THIS DEMO WON'T WORK!
 * Created by hug.
 */
public class DemoIntegerHopPuzzleSolution {
    public static void main(String[] args) {
        int start = 17;
        int goal = 111;

        IntegerHopGraph ahg = new IntegerHopGraph();

        ShortestPathsSolver<Integer> solver = new LazySolver<>(ahg, start, goal, 10);
        SolutionPrinter.summarizeSolution(solver, " => ");

    }
}
