package model.events;

import model.entities.Entity;

/**
 * Models a Punch event.
 */
public class PunchEvent extends AbstractEntityEvent {

    /**
     * @param source
     *             the entity who post the event
     */
    public PunchEvent(final Entity source) {
        super(source);
    }
}
