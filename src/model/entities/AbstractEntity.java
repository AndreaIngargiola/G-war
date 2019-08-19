package model.entities;

import org.jbox2d.common.Vec2;

import com.google.common.eventbus.EventBus;

import Test.Main;
import model.components.EntityBody;
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
    private final EntityBody body;
    private final Translator<EntityComponent> components = new TranslatorImpl<>(EntityComponent.class);
    private final Faction type;
    private boolean isAlive = true;

    /** 
     * @param type
     *            the faction the entity belongs to
     * @param body
     *            an {@link EntityBody} object
     */
    public AbstractEntity(final Faction type, final EntityBody body) {
        this.type = type;
        this.body = body;
        body.attach(this);
        body.setUserData(this);
    }


    @Override
    public final Translator<EntityComponent> getComponents() {
        return components;
    }

    @Override
    public final Faction getType() {
        return type;
    }

    @Override
    public final EntityBody getBody() {
        return body;
    }


    @Override
    public final void update(final double dt) {
        components.stream().forEach(c -> c.update(dt));
    }

    /**
     * Generates a @Death event and then detaches all components.
     */
    @Override
    public void destroy() {
        post(new Death(this));
        components.clear();
        remove(body);
        Main.getWorld().destroyBody(body.getBody());
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

    @Override
    public final float getTopSide() {
        return  body.getPosition().y - body.getDimension().y / 2;
    }

    @Override
    public final float getLeftSide() {
        return body.getPosition().x - body.getDimension().x / 2;
    }

    @Override
    public final float getBottomSide() {
        return (body.getPosition().y + body.getDimension().y / 2);
    }

    @Override
    public final float getRightSide() {
        return (body.getPosition().x + body.getDimension().x / 2);
    }

    @Override
    public final Vec2 getCenter() {
        return body.getPosition();
    }

    @Override
    public final void setIsAlive(final boolean isAlive) {
        this.isAlive = isAlive;
    }

    @Override
    public final boolean getIsAlive() {
        return isAlive;
    }
}
