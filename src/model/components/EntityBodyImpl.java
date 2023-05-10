package model.components;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import model.entities.Entity;

/**
 * An implementation of the interface {@link EntityBody}.
 */
public final class EntityBodyImpl extends AbstractEntityComponent implements EntityBody {

    private final Body body;
    private final Vec2 dimension;

    /**
     * 
     * @param body
     *           the body
     * @param dimension
     *           the dimension
     */
    public EntityBodyImpl(final Body body, final Vec2 dimension) {
        super();
        this.body = body;
        this.dimension = dimension;
    }

    @Override
    public Vec2 getPosition() {
        return body.getPosition();
    }

    @Override
    public Vec2 getDimension() {
        return dimension;
    }

    @Override
    public void setLinearVelocity(final Vec2 velocity) {
        body.setLinearVelocity(velocity);
    }

    @Override
    public Vec2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public void applyImpulse(final Vec2 impulse) {
        body.applyLinearImpulse(impulse.mul(body.getMass()), body.getWorldCenter());
    }

    @Override
    public void setUserData(final Entity entity) {
        body.setUserData(entity);
    }
    @Override
    public Body getBody() {
        return body;
    }

}
