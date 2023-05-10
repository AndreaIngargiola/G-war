package model.events;

import model.entities.Entity;

/**
 * Models a Damage event.
 * When an entity loses health points.
 *
 */

public class LifeChange extends AbstractEntityEvent {

    private final int lifePoints;

    /**
     * 
     * @param source
     *           the entity that loses health points
     * @param lifePoints
     *           the number of health points of the entity
     */
    public LifeChange(final Entity source, final int  lifePoints) {
        super(source);
        this.lifePoints = lifePoints;
    }

    /**
     * 
     * @return the number of health points that the source loses
     */
    public int getLife() {
        return this.lifePoints;
    }

}
