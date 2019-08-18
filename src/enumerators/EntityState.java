package enumerators;

/**
 * An enumerator for the different states an entity view can take.
 */
public enum EntityState {

    /**
     * When the entity is walking.
     */
    WALKING,

    /**
     * When the entity is under attack.
     */
    ANGRY,

    /**
     * When the entity is punching.
     */
    PUNCH,

    /**
     * When a determinate component of the entity is not active.
     */
    OFF,

    /**
     * When a determinate component of the entity is active.
     */
    ON;

}
