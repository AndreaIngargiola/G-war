package model.entities;

import org.jbox2d.common.Vec2;

import enumerators.Faction;
import model.components.EntityBody;
import model.components.EntityComponent;
import model.events.EntityEvent;
import model.events.EntityEventSubscriber;
import utils.Translator;


/**
 * Models a base interface of a generic entity of the game.
 */

public interface Entity {

    /**
     * 
     * @return components
     */
    Translator<EntityComponent> getComponents();

    /**
     * 
     * @return type
     */
    Faction getType();

    /**
     * @return the related {@link EntityBody} object.
     */
    EntityBody getBody();

    /**
     * Gets a component by its interface.
     * 
     * @param <C>
     *            Type of components
     * @param component
     *            Interface of the component you want to get
     * @return the component
     */
    <C extends EntityComponent> C get(Class<C> component);

    /**
     * Removes a {@link EntityComponent} object from the entity.
     * 
     * @param component
     *            The component to remove.
     */
    void remove(EntityComponent component);

    /**
     * Adds a {@link EntityComponent} object to the entity.
     * 
     * @param component
     *            The component to add
     */
    void add(EntityComponent component);

    /**
     * Synchronizes the entities.
     * 
     * @param dt
     *            The time delta (in seconds) since the last call
     */
    void update(double dt);

    /**
     * Destroys this entity.
     */
    void destroy();

    /**
     * Registers a listener for {@link EntityEvent} objects.
     * 
     * @param listener
     *            the listener
     */
    void register(EntityEventSubscriber listener);

    /**
     * Unregisters the listener for {@link EntityEvent} objects.
     * 
     * @param listener
     *            the listener
     */
    void unregister(EntityEventSubscriber listener);

    /**
     * Publishes a {@link EntityEvent} object.
     * 
     * @param event
     *            The event to publish
     */
    void post(EntityEvent event);

    /**
     * @return the top side position of the entity
     */
    float getTopSide();

    /**
     * @return the left side position of the entity
     */
    float getLeftSide();

    /**
     * @return the bottom side position of the entity
     */
    float getBottomSide();

    /**
     * @return the right side position of the entity
     */
    float getRightSide();

    /**
     * @return the center of the entity box
     */
    Vec2 getCenter();

    /**
     * 
     * @param alive
     *               true if the entity is alive
     */
    void setAlive(boolean alive);

    /**
     * 
     * @return true if the entity is alive
     */
    boolean isAlive();
}
