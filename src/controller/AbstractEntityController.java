package controller;

import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import model.entities.Entity;
import model.events.Death;
import viewGame.EntityView;

/**
 * Base implementation of {@link EntityController}.
 * 
 * @param <V>
 *           generic {@link EntityView} type
 */
public abstract class AbstractEntityController<V extends EntityView> implements EntityController {

    private final Entity entity;
    private final V entityView;


    /**
     * @param entity
     *            the {@link Entity} object to control
     * @param entityView
     *            the {@link EntityView} object to update
     */
    public AbstractEntityController(final Entity entity, final V entityView) {
        this.entity = entity;
        this.entityView = entityView;
        entity.register(this);
        entityView.setDimension(new Dimension2D(entity.getBody().getDimension().x, entity.getBody().getDimension().y));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void update(final double dt) {
        if (!getEntityModel().getIsAlive()) {
            this.getEntityModel().destroy();
        } else {
            getEntityModel().getComponents().stream().forEach(e -> e.update(dt));
            getEntityView().setPosition(new Point2D(getEntityModel().getBody().getPosition().x, getEntityModel().getBody().getPosition().y));
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deathListener(final Death event) {
        event.getSource().unregister(this);
        getEntityView().remove();
    }

    @Override
    public final Entity getEntityModel() {
        return entity;
    }

    @Override
    public final V getEntityView() {
        return entityView;
    }

}
