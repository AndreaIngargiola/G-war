package model.components;

import enumerators.CollisionSide;
import model.entities.Entity;


/**
 * Models a class that takes care of the collisions between the player and another entity.
 *
 */
public interface CollisionHandler extends EntityComponent {

    /**
     * 
     * @param source
     *            the entity
     * @param other
     *           the entity the source collied with
     * @param side
     *           the side the source collided
     */
    void collision(Entity source, Entity other, CollisionSide side);

}
