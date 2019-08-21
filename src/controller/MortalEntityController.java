package controller;


import com.google.common.eventbus.Subscribe;

import model.entities.Entity;
import model.events.ChangeDirectionEvent;
import model.events.CollisionEvent;
import viewGame.MortalEntityView;

/**
 * An extension of {@link AbstractEntityController} for mortal entity.
 */
public class MortalEntityController extends AbstractEntityController<MortalEntityView> {

    /**
     * @param entity
     *            The {@link Entity} object to control.
     * @param entityView
     *            The {@link LivingEntityView} object to update.
     */
    public MortalEntityController(final Entity entity, final MortalEntityView entityView) {
        super(entity, entityView);
    }

    /**
     * 
     * @param event
     *           a {@link ChangeDirectionEvent}
     */
    @Subscribe
    public void changeDirectionistener(final ChangeDirectionEvent event) {
        getEntityView().changeDirection(event.getDirection());
    }

    /**
     * 
     * @param event
     *          a {@link CollisionEvent}
     */
    @Subscribe
    public void collisionListener(final CollisionEvent event) {
        getEntityView().collisionSound();
    }

}
