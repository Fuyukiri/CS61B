package PlusWorld;

import byowTools.TileEngine.TERenderer;
import byowTools.TileEngine.TETile;
import byowTools.TileEngine.Tileset;

import java.util.*;

/**
 * Draws a world consisting of plus shaped regions.
 */
public class PlusWorld {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;
    private static final int[][] directions = new int[][] {
            {-1, -2}, {1, 2}, {-2, 1}, {2, -1}
    };
    static Set<Point> visited;
    private static class Point {
        private final int x;
        private final int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Point)) return false;
            Point p = (Point) o;
            return p.x == x && p.y == y;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + x;
            result = 31 * result + y;
            return result;
        }

        @Override
        public String toString() {
            return "x :" + x + " y: " + y;
        }
    }
    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // Helper to Tesselation of Pluses
        visited = new HashSet<>();
        // initialize tiles
        var world = new TETile[WIDTH][HEIGHT];
        for (var x = 0; x < WIDTH; x += 1) {
            for (var y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.GRASS;
            }
        }
        int size = 4;

        var x = new Random().nextInt(WIDTH);
        var y = new Random().nextInt(HEIGHT);
        var p = new Point(x, y);
        tessellate(world, p, size);
        // draws the world to the screen
        ter.renderFrame(world);
    }

    private static void addPlus(TETile[][] world, int size, TETile tile, Point p) {
        for (var x = p.x; x < p.x + size * 3; x++) {
            for (var y = p.y; y < p.y + size * 3; y++) {
                if (x >= WIDTH || y >= HEIGHT) continue;
                if (x < 0 || y < 0) continue;
                // left bottom
                if (x < p.x + size && y < p.y + size) continue;
                // right bottom
                if (x >= p.x + size * 2 && y < p.y + size) continue;
                // left top
                if (x < p.x + size && y >= p.y + size * 2) continue;
                // right top
                if (x >= p.x + size * 2 && y >= p.y + size * 2) continue;
                world[x][y] = tile;
            }
        }
    }

    /*
    p is the start point of tessellation
    bfs algorithm
     */
    public static void tessellate(TETile[][] world, Point p, int size) {
        // fill left area:
        ArrayDeque<Point> queue = new ArrayDeque<>();
        queue.add(p);

        while (!queue.isEmpty()) {
            var colorVariant = TETile.colorVariant(Tileset.FLOWER, 255, 255, 255, new Random());
            var currPoint = queue.removeFirst();
            var x = currPoint.x;
            var y = currPoint.y;
            if (!checkAvailability(currPoint, size)) continue;
            addPlus(world, size, colorVariant, currPoint);
            blockingArea(currPoint, size);

            for (var direction : directions)  {
                var newX = x + direction[0] * size;
                var newY = y + direction[1] * size;
                var next = new Point(newX, newY);
                if (!checkAvailability(next, size)) {
                    continue;
                }
                if (newX >= -size * 2 && newY >= -size * 2  && newX <= WIDTH + size && newY <= HEIGHT + size) {
                    queue.addLast(next);
                }
            }
        }
    }

    private static void blockingArea(Point p, int size) {
        var list = getPlusPointList(p, size);
        visited.addAll(list);
    }

    private static boolean checkAvailability(Point p, int size) {
        var list = getPlusPointList(p, size);
        for (var e : list) {
            if (visited.contains(e)) {
                return false;
            }
        }
        return true;
    }

    private static List<Point> getPlusPointList(Point p, int size) {
        List<Point> list = new ArrayList<>();
        list.add(new Point(p.x + size, p.y + size));
        list.add(new Point(p.x + size, p.y));
        list.add(new Point(p.x, p.y + size));
        list.add(new Point(p.x + 2 * size, p.y + size));
        list.add(new Point(p.x + size, p.y + 2 * size));
        return list;
    }
}
