package model.entities;

import com.google.common.eventbus.EventBus;

import enumerators.Faction;
import model.components.EntityComponent;
import model.events.Death;
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
    private final Faction type;


    /** 
     * @param body
     *            An {@link EntityBody} object is the only required component for an entity.
     */
    public AbstractEntity(final Faction type /*,final EntityBody body*/) {
        this.type = type;
        //this.body = body;
        //body.attach(this);
    }

    @Override
    public final Translator<EntityComponent> getComponents() {
        return components;
    }

    @Override
    public final Faction getType() {
        return type;
    }

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
    @Override
    public void destroy() {
        post(new Death(this));
        //components.forEach(this::remove); //?? Da un'exception
        components.clear();
//        remove(body);
    }

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