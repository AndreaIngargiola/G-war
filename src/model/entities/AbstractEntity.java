package model.entities;

import com.google.common.eventbus.EventBus;

import model.components.EntityComponent;
import model.events.EntityEvent;
import model.events.EntityEventSubscriber;
import utils.Translator;
import utils.TranslatorImpl;

/**
 * Represents an abstract implementation of the base interface {@link Entity}.
 */

public abstract class AbstractEntity implements Entity {

    private final EventBus eventBus = new EventBus();
    //private final EntityBody body;
    private final Translator<EntityComponent> components = new TranslatorImpl<>(EntityComponent.class);

    /** 
     * @param body
     *            An {@link EntityBody} object is the only required component for an entity.
     */
//    public AbstractEntity(final EntityBody body) {
//        this.body = body;
//        body.attach(this);
//    }

//    @Override
//    public final EntityBody getBody() {
//        return body;
//    }


    @Override
    public void update(final double dt) {
        components.forEach(c -> c.update(dt));
    }

    /**
     * Generates a @DestructionEvent and then detaches all components.
     */
//    @Override
//    public void destroy() {
//        post(new DestructionEvent(this));
//        components.forEach(this::remove);
//        remove(body);
//    }

    /**
     * An {@link EntityEventListener} object declares wanted events with the @Subscribe annotation.
     */
    @Override
    public final void register(final EntityEventSubscriber listener) {
        eventBus.register(listener);
    }

    @Override
    public final void unregister(final EntityEventSubscriber listener) {
        eventBus.unregister(listener);
    }

    @Override
    public final void post(final EntityEvent event) {
        eventBus.post(event);
    }

    @Override
    public final <C extends EntityComponent> C get(final Class<C> component) {
        return components.get(component);
    }

    @Override
    public final void add(final EntityComponent component) {
        components.put(component);
        component.attach(this);
    }

    @Override
    public final void remove(final EntityComponent component) {
        components.remove(component);
        component.detach();
    }

}
