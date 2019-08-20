package model.components;

import java.util.Optional;

import model.entities.Entity;

/**
 * Models a generic entity component.
 */
public interface EntityComponent {

    /**
     * Attaches the component to an {@link Entity} object.
     * 
     * @param owner
     *            The {@link Entity} object owner
     * @throws IllegalStateException
     *             A component cannot be attached to more than one entity
     */
    void attach(Entity owner) throws IllegalStateException;

    /**
     * Detaches the component.
     */
    void detach();

    /**
     * Synchronizes the component.
     * 
     * @param dt
     *            Delta time since last call in seconds.
     */
    void update(double dt);

    /**
     * 
     * @return The entity to which its attached to.
     */
    Optional<? extends Entity> getOwner();

}
