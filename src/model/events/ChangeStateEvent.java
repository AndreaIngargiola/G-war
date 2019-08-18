package model.events;

import enumerators.EntityState;
import model.entities.Entity;

/**
 * Models a CangeState event. 
 */
public class ChangeStateEvent extends AbstractEntityEvent {

    private final EntityState state;

    /**
     * 
     * @param source
     *          the entity the post the event
     * @param state
     *          the new entity state
     */
    public ChangeStateEvent(final Entity source, final EntityState state) {
        super(source);
        this.state = state;
    }

    /**
     * 
     * @return the entity state
     */
    public EntityState getState() {
        return this.state;
    }

}
