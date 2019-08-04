package model.components;

import model.events.PointsEvent;
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
    public final void pointsListener(final PointsEvent pointsEvent) {
        this.current += pointsEvent.getPoints();
    }

    @Override
    public final int getCurrent() {
        return this.current;
    }

    @Override
    public final String toString() {
        return "Points";
    }
}
