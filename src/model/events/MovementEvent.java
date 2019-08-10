package model.events;

import java.util.Optional;

import org.jbox2d.common.Vec2;

import model.entities.Entity;

/**
 * Models a Movement event.
 *
 */
public class MovementEvent extends AbstractEntityEvent {

    private Optional<Vec2> movement = Optional.empty();

    /**
     * @param source
     *            the entity who is moving
     * @param movement
     *            the direction it is moving
     */
    public MovementEvent(final Entity source, final Vec2 movement) {
        super(source);
        this.movement = Optional.of(movement);
        System.out.println("MovementEvent");
        
    }
    /**
     * 
     * @return the number of points that the source gets
     */
    public Vec2 getMovement() {
        return this.movement.get();
    }
}
