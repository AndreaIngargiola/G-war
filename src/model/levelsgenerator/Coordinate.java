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

    /**
     * Get a new coordinate that is the vector sum of two points (this, and the argument toSum).
     * @param toSum is the second addend.
     * @return the vector sum of this coordinate and the toSum.
     */
    public Coordinate sum(final Coordinate toSum) {
        return new Coordinate(this.p.x + toSum.getPoint().x, this.p.y + toSum.getPoint().y);
    }

    /**
     * Get a new coordinate that is the vector sum of two points (this, and the argument toSubtract with inverted signs).
     * @param toSubtract is the coordinate to subtract.
     * @return the vector sum of this coordinate and the toSum.
     */
    public Coordinate sub(final Coordinate toSubtract) {
        Coordinate toSub = new Coordinate(-toSubtract.getPoint().x, -toSubtract.getPoint().y);
        return this.sum(toSub);
    }

}
