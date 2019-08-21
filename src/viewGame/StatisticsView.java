package viewGame;

import javafx.scene.Node;

/**
 * Models the view of the life and the points of the player.
 */
public interface StatisticsView {

    /**
     * @return the screen's related node.
     */
    Node getRoot();

    /**
     * Sets current health level.
     * 
     * @param life
     *            current health level
     */
    void setCurrentHealth(int life);


    /**
     * Set the number of points.
     * 
     * @param points
     *            the number of points
     */
    void setPoints(int points);
    
    /**
     * 
     * @return currentPoint
     */
    int getCurrentPoint();
    
    void setMaxHealth();
}
