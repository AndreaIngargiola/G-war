package controller;

import model.entities.Entity;
import viewGame.ImmortalEntityView;

/**
 * An extension of {@link AbstractEntityController} for immortal entity.
 *
 */
public class ImmortalEntityController extends AbstractEntityController<ImmortalEntityView> {

    /**
     * @param entity
     *            The {@link Entity} object to control.
     * @param entityView
     *            The {@link LifelessEntityView} object to update.
     */
    public ImmortalEntityController(final Entity entity, final ImmortalEntityView entityView) {
            super(entity, entityView);
        }

}
