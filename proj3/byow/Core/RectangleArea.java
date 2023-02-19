package byow.Core;

public class Area {
    private Point roomEntry;
    private final int WIDTH;
    private final int HEIGHT;

    // We define the left bottom corner of the area is the area entry
    Area(Point roomEntry, int width, int height) {
        this.roomEntry = roomEntry;
        WIDTH = width;
        HEIGHT = height;
    }

    /*
    Check if input area is overlap with current area
     */
    public boolean isOverLap(Point point, int width, int height) {
        var xMin = point.getX();
        var yMin = point.getY();
        var xMax = xMin + width;
        var yMax = yMin + height;

        // xMin   xMax 
    }
}
