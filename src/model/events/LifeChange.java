package model.events;

import model.entities.Entity;

/**
 * Models a Damage event.
 * When an entity loses health points.
 *
 */

public class Damage extends AbstractEntityEvent {

    private final int loss;

    /**
     * 
     * @param source
     *           the entity that loses health points
     * @param loss
     *           the number of health points that the source loses 
     */
    public Damage(final Entity source, final int  loss) {
        super(source);
        this.loss = loss;
    }

    /**
     * 
     * @return the number of health points that the source loses
     */
    public int getLoss() {
        return this.loss;
    }

}
