package model.components;


/**
 * Counter of points (int number) of the player.
 * The player can get points by killing enemies and by moving ahead in the game.
 */

public interface Points extends EntityComponent {

    /**
     * 
     * @param points
     *             the number of points the entity earns
     */
    void addPoints(int points);

    /**
     * 
     * @return the current points
     */
    int getCurrent();
}
