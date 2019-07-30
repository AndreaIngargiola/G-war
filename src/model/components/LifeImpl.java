package model.components;

import model.events.Damage;

/**
 * Implementation class for the interface {@link Life} .
 */

public class LifeImpl extends AbstractEntityComponent implements Life {

    private int current;

    /**
     * 
     * @param current
     *              Current number of health points
     */
    public LifeImpl(final int current) {
        super();
        this.current = current;
        if (current < 0) {
            throw new IllegalArgumentException("Current life cannot below zero");
        }
    }

    @Override
    public final void demageListener(final Damage damage) {
        this.current -= damage.getLoss();
        if (this.current <= 0) {
            damage.getSource().destroy();
        }
    }

    @Override
    public final int getCurrent() {
        return this.current;
    }
}
