package model.components;

import model.events.PointsChangeEvent;

/**
 * Implementation class for the interface {@link Points} .
 */

public class PointsImpl extends AbstractEntityComponent implements Points {

    private int current;

    /**
     * Set the points at 0.
     */
    public PointsImpl() {
        super();
        this.current = 0;
    }

    @Override
    public final void addPoints(final int points) {
        this.current += points;
        post(new PointsChangeEvent(this.getEntity(), this.current));
    }

    @Override
    public final String toString() {
        return "Points";
    }

    @Override
    public final int getCurrent() {
        return this.current;
    }
}
