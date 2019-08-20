package model.events;

import model.entities.Entity;

/**
 * Models an EndCollision event. 
 *
 */
public class EndCollision extends AbstractEntityEvent {

    /**
     * 
     * @param source
     *         the entity the post the event
     */
    public EndCollision(final Entity source) {
        super(source);
    }

}
