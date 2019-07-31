package model.events;

import model.entities.Entity;

/**
 * Model a Death event
 * When an entity is being removed from the game.
 */

public class Death extends AbstractEntityEvent {

    /**
     * Generated when an {@link Entity} object is being removed from the game.
     * 
     * @param source
     *            The entity being removed.
     */
    public Death(final Entity source) {
        super(source);
    }

}
