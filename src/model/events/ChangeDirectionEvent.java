package model.events;

import enumerators.HorizontalDirection;
import model.entities.Entity;

/**
 * Model a ChangeDirection event
 * When a moving entity change his direction.
 */
public class ChangeDirectionEvent extends AbstractEntityEvent {

    private final HorizontalDirection direction;

    /**
     * 
     * @param source
     *            the entity the post the event
     * @param direction
     *            the direction
     */
    public ChangeDirectionEvent(final Entity source, final HorizontalDirection direction) {
        super(source);
        this.direction = direction;
    }

    /**
     * 
     * @return the direction
     */
    public HorizontalDirection getDirection() {
        return direction;
    }
}
