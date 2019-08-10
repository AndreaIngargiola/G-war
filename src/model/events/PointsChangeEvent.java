package model.events;


import model.entities.Entity;

/**
 * Models a points event.
 * When an entity (the player) get point.
 *
 */

public class PointsEvent extends AbstractEntityEvent {

    private final int points;

    /**
     * 
     * @param source
     *           the entity that gets points
     * @param points
     *           the number of points that the source gets
     */
    public PointsEvent(final Entity source, final int  points) {
        super(source);
        this.points = points;
    }

    /**
     * 
     * @return the number of points that the source gets
     */
    public int getPoints() {
        return this.points;
    }

}
