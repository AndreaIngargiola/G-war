package model.components;

import model.events.LifeChange;

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
    public final void demage(final int loss) {
        this.current -= loss;
        System.out.println("toglie vita" + this.getEntity());
        if (this.current <= 0) {
            System.out.println("morto");
            this.getEntity().setIsAlive(false);
            post(new LifeChange(this.getEntity(), 0));
        } else {
            post(new LifeChange(this.getEntity(), this.current));
        }
    }

    @Override
    public final String toString() {
        return "Life";
    }
}
