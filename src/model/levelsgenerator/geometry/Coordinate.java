package model.levelsgenerator.geometry;
import java.awt.Point;
import java.util.Objects;

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
        final Coordinate toSub = new Coordinate(-toSubtract.getPoint().x, -toSubtract.getPoint().y);
        return this.sum(toSub);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(p);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Coordinate)) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        return Objects.equals(p, other.p);
    }
}
