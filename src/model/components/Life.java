package model.components;


/**
 * Models an entity life as a counter of health points (int number).
 */

public interface Life extends EntityComponent {

    /**
     * 
     * @param loss
     *            the number of health points that the entity loses 
     */
    void demage(int loss);

}
