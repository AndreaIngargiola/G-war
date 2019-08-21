package controller;

import com.google.common.eventbus.Subscribe;

import model.entities.Entity;
import model.events.Death;
import model.events.EntityEventSubscriber;
import view.entities.EntityView;

/**
 * Base interface of an entity's controller.
 */
public interface EntityController extends EntityEventSubscriber {

    /**
     * Used to synchronize the entity controller.
     *
     * @param dt
     *            delta time since last call in seconds
     */
    void update(double dt);

    /**
     * It manages the entity destruction at a {@link Death} event signal.
     * 
     * @param event
     *            The {@link Death} event
     */
    @Subscribe
    void deathListener(Death event);

    /**
     * 
     * @return the related {@link Entity} class
     */
    Entity getEntityModel();

    /**
     * 
     * @return the related {@link EntityView} class
     */
    EntityView getEntityView();
}
