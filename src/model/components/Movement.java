package model.components;

import org.jbox2d.common.Vec2;

import enumerators.HorizontalDirection;


/**
 * Models a generic entity movement.
 */
public interface Movement extends EntityComponent {

    /**
     * 
     */
    void move();

    /**
     * 
     * @param movement
     *         the movement as a vector.
     */
    void move(Vec2 movement);

    /**
     * 
     * @return the walking speed
     */
    float getWalkSpeed();

    /**
     * 
     * @return the jumping speed
     */
    float getJumpSpeed();

    /**
     * Change the direction of the entity.
     */
    void changeDirection();

    /**
     * Stops the entity.
     */
    void stop();

    /**
     * 
     * @return the direction of the entity
     */
    HorizontalDirection getFaceDirection();

    /**
     * 
     * @return true if the entity is not jumping
     */
    boolean isOnGround();
}
