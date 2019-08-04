package model.components;

import com.google.common.eventbus.Subscribe;

import model.events.PointsEvent;

/**
 * Counter of points (int number) of the player.
 * The player can get points by killing enemies and by moving ahead in the game.
 */

public interface Points extends EntityComponent {

    /**
     * 
     * @param pointsEvent
     *              an event
     */
    @Subscribe
    void pointsListener(PointsEvent pointsEvent);

    /**
     * 
     * @return the current number  point
     */
    int getCurrent();
}
