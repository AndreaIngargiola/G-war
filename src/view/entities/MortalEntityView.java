package view.entities;

import enumerators.HorizontalDirection;

/**
 * An interface that models a view for MortalEntity.
 */
public interface MortalEntityView extends EntityView {

    /**
     * 
     * @param direction
     *               the new direction
     */
    void changeDirection(HorizontalDirection direction);

    /**
     * Change into a punch sprite.
     */
    void punch();

    /**
     * Go back to walking sprite at the end of the punch.
     */
    void stopPunch();

    /**
     * Change into an angry sprite when the entity is under attack.
     */
    void angryAnimation();

    /**
     * Go back to walking sprite at the end of the attack.
     */
    void endAngryAnimation();

    /**
     * 
     * @param life
     *          new number of life points
     */
    void changeLife(int life);

    /**
     * 
     * @param points
     *           new number of points 
     */
    void changePoints(int points);

    /**
     * Make a sound when the entity jumps.
     */
    void makeJumpSound();

    /**
     * Make a sound when the entity collides.
     */
    void makeCollisionSound();
}
