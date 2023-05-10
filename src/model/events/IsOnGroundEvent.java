package model.events;

import model.entities.Entity;

/**
 * Models an event who is called when we want to know if the entity who called the event is in contact 
 * with the entity right beside him.
 */
public class IsOnGroundEvent extends AbstractEntityEvent {

    private final float entityTop;

    /**
     * 
     * @param source
     *            the entity who called the event
     * @param entityTop
     *            the position of the top of the entity right beside the source
     */
    public IsOnGroundEvent(final Entity source, final float entityTop) {
        super(source);
        this.entityTop = entityTop;
    }

    /**
     * 
     * @return
     *        entityTop
     */
    public float getEntityTop() {
        return this.entityTop;
    }
}
