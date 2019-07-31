package enumerators;

/**
 * An enumerator with the possible sides the player can collide with an entity.
 *
 */
public enum CollisionSide {

    /**
     * When the player jump over another entity.
     */
    BOTTOM,

    /**
     * When another entity collided with the top of the player.
     */
    TOP,

    /**
     * When the player collides with another entity on the side.
     */
    SIDE;

}
