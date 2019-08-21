package model.components;

/**
 * Models a basic attack (when an enemy collides with the player side by side).
 */

public interface Attack extends EntityComponent {

    /**
     * 
     * @return the number of health points he takes away from the target
     */
    int getDamage();
}
