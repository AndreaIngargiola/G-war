package model.levelsgenerator;
import java.awt.Point;

/**
 * is an utility class that wrap the java awt Point, making it immutable once generated.
 */
public class Coordinate {

    private final Point p;

    /**
     * Build the coordinate based on the inner field point.
     * @param x is the x coordinate of the point.
     * @param y is the y coordinate of the point.
     */
    public Coordinate(final int x, final int y) {
        p = new Point(x, y);
    }

    /**
     * Get the inner private point. 
     * @return the inner point with the integer x and y coordinates.
     */
    public Point getPoint() {
        return this.p;
    }

}
