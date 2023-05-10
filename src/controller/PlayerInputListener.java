package controller;

import javafx.geometry.Point2D;

/**
 * Interface for player input.
 */
public interface PlayerInputListener {
    /**
     * Called when the player wants to move towards a position.
     * 
     * @param direction
     *            The direction vector.
     */
    void move(Point2D direction);

    /**
     * Called when the player wants to punch.
     */
    void punch(); 

    /**
     * Called when the punch stops.
     */
    void stopPunch();

    /**
     * Make the player stop his movement.
     */
    void stop();
}
