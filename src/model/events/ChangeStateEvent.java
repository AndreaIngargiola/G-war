package model.events;

import model.entities.Entity;

/**
 * Models a Timer event. 
 */
public class TimerEvent extends AbstractEntityEvent {

    /**
     * 
     * @param source
     *             the entity the post the event
     */
    public TimerEvent(final Entity source) {
        super(source);
    }

}
