package model.events;

import model.entities.Entity;

/**
 * Models a Collision Event.
 * When two entities collides.
 */

public class CollisionEvent extends AbstractEntityEvent {

    /**
     * 
     * @param source
     *             it's always the player
     */
    public CollisionEvent(final Entity source) {
        super(source);

    }



}
