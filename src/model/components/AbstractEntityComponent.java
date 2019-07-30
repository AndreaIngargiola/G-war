package model.components;

import java.util.Optional;

//import com.google.common.eventbus.Subscribe;

import model.entities.Entity;
import model.events.EntityEvent;
import model.events.EntityEventSubscriber;

/**
 *  Represents an abstract implementation of the base interface {@link EntityComponent}.
 */

public abstract class AbstractEntityComponent implements EntityComponent, EntityEventSubscriber {

    private Optional<Entity> owner = Optional.empty();

    @Override
    public final Optional<? extends Entity> getOwner() {
        return owner;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void attach(final Entity owner) throws IllegalStateException {
        this.owner.ifPresent(e -> {
            throw new IllegalStateException("Component already attached to an entity");
        });
        this.owner = Optional.of(owner);
        owner.register(this);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void detach() throws IllegalStateException {
        owner.ifPresent(entity -> {
            entity.unregister(this);
            owner = Optional.empty();
            entity.remove(this);
        });
    }

    @Override
    public void update(final double dt) {
        //
    }

    /**
     * Should the component detach on death?
     * Defaults to true.
     * 
     * @return true if the component should detach on death.
     */
    protected boolean detachesOnDeath() {
        return true;
    }

    /**
     * Called on life changes (if the owner has a {@link Life} component).
     * 
     * @param event
     *            The related {@link LifeEvent} instance
     */
    //@Subscribe
    //protected void onLifeChange(final LifeEvent event) {
     //   if (detachesOnDeath() && event.isDead()) {
      //      detach();
    //    }
   // }

    /**
     * Convenience method to avoid the optional.
     * 
     * @return The related {@link Entity} object.
     * 
     * @throws IllegalStateException
     *             if the component is not attached to an Entity
     */
    protected final Entity getEntity() {
        return owner.orElseThrow(IllegalStateException::new);
    }

    /**
     * Generates an {@link EntityEvent} object.
     * 
     * @param event
     *            The event
     */
    protected final void post(final EntityEvent event) {
        getEntity().post(event);
    }

}
