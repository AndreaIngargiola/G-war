package model;

/**
 * An interface for the game loop class.
 */
public interface GameModel {
    /**
     * Updates the status of the active entities in the model.
     * @param dt is the time interval of update of the simulation.
     * @return true if the player is alive, false otherwise.
     */
    boolean updateEntity(double dt);

    /**
     * Start the game loop.
     */
    void start();

    /**
     * Stop the game loop.
     */
    void stop();
}
