package model.physics;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import javafx.geometry.Point2D;

public class Utils {
 
	private Utils() {
    }

    /**
     * Converts a {@link Vec2} to a {@link Point2D}.
     * 
     * @param vector
     *            The Vec2
     * @return The Point2D
     */
    public static Point2D vecToPoint(final Vec2 vector) {
        if (!vector.isValid()) {
            // weird Box2D behaviour fixes..
            return Point2D.ZERO;
        }
        return new Point2D(vector.x, vector.y);
    }

    /**
     * Converts a {@link Point2D} to a {@link Vec2}.
     * 
     * @param point
     *            The Point2D
     * @return The Vec2
     */
    public static Vec2 pointToVec(final Point2D point) {
        return new Vec2((float) point.getX(), (float) point.getY());
    }
   
    
}
