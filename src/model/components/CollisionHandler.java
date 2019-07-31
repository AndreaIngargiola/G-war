package model.components;

import com.google.common.eventbus.Subscribe;

import model.events.CollisionEvent;

/**
 * Models a class that takes care of the collisions between the player and another entity.
 *
 */
public interface CollisionHandler extends EntityComponent {

    /**
     * 
     * @param collisionEvent
     *                    an event
     */
    @Subscribe
    void collisionListener(CollisionEvent collisionEvent);

}
