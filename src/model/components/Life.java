package model.components;

import com.google.common.eventbus.Subscribe;

import model.events.Damage;

/**
 * Models an entity life as a counter of health points (int number).
 */

public interface Life extends EntityComponent {

    /**
     * 
     * @param damage
     *              an event
     */
    @Subscribe
    void demageListener(Damage damage);

    /**
     * 
     * @return the current number of health point of the entity
     */
    int getCurrent();
}
