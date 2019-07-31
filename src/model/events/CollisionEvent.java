package model.events;

import enumerators.CollisionSide;
import model.entities.Entity;

/**
 * Models a Collision Event.
 * When two entities collides.
 */

public class CollisionEvent extends AbstractEntityEvent {

    private final Entity other;
    private final CollisionSide side;

    /**
     * 
     * @param source
     *             it's always the player
     * @param other
     *             the other he collides with
     * @param side
     *             the player's side that collides with the other
     */
    public CollisionEvent(final Entity source, final Entity other, final CollisionSide side) {
        super(source);
        this.other = other;
        this.side = side;
    }

    /**
     * 
     * @return other
     */
    public Entity getOther() {
        return other;
    }

    /**
     * 
     * @return side
     */
    public CollisionSide getSide() {
        return side;
     }

}
