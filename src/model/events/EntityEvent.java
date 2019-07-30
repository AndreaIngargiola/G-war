package model.events;

import model.entities.Entity;

/**
 * Generic event for {@link Entity}.
 */

public interface EntityEvent {

    /**
     * 
     * @return The {@link Entity} which generated this event.
     */
    Entity getSource();
}
