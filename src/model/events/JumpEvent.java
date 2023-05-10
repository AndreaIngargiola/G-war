package model.events;

import model.entities.Entity;

/**
 * Models a Jump event.
 */
public class JumpEvent extends AbstractEntityEvent {

    /**
     * 
     * @param source
     *            the entity the post the event
     */
    public JumpEvent(final Entity source) {
        super(source);
    }

}
