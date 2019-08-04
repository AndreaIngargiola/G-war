package model.physics;

import java.util.Optional;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import model.components.EntityBody;
import model.components.EntityBodyImpl;

public class BodyBuilderImpl implements BodyBuilder {
	private static final Vec2 GRAVITY = new Vec2(0, 10f);
	private final World world = new World(GRAVITY);

    private Optional<Vec2> pos = Optional.empty();
    private Optional<Vec2> dimension = Optional.empty();
    private Optional<Float> surfaceFriction = Optional.empty();
    private boolean isSubjectToForces = true;
    private boolean isSolid = true;
    private boolean isMoveable = true;


    @Override
    public BodyBuilder setSubjectToForces(final boolean opt) {
        isSubjectToForces = opt;
        return this;
    }

    @Override
    public BodyBuilder setPosition(final Vec2 position) {
        pos = Optional.of(position);
        return this;
    }

    @Override
    public BodyBuilder setSize(final Vec2 size) {
        dimension = Optional.of(size);
        return this;
    }

    @Override
    public BodyBuilder setIsSolid(final boolean solid) {
        isSolid = solid;
        return this;
    }

    @Override
    public BodyBuilder setIsMoveable(final boolean moveable) {
        isMoveable = moveable;
        return this;
    }

    @Override
    public BodyBuilder setFriction(final double friction) {
        // we accept precision loss because Box2D works this way.
        surfaceFriction = Optional.of((float) friction);
        return this;
    }

    @Override
    public EntityBody build() {
        checkDataValidity();

        final BodyDef bodyDef = new BodyDef();
        bodyDef.setFixedRotation(true);
        bodyDef.setType(makeType());
        bodyDef.setPosition(pos.get());
        final Body body = world.createBody(bodyDef); 		//world.get()
        final FixtureDef fixtureDef = new FixtureDef();
        surfaceFriction.ifPresent(fixtureDef::setFriction);
        fixtureDef.setSensor(!isSolid);
        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(dimension.get().x / 2,  dimension.get().y / 2);
        fixtureDef.setShape(shape);
        body.createFixture(fixtureDef);
        body.resetMassData();
        
        return new EntityBodyImpl(body, dimension.get());
    }

    private BodyType makeType() {
        if (!isMoveable) {
            return BodyType.STATIC;
        }
        if (!isSubjectToForces) {
            return BodyType.KINEMATIC;
        }
        return BodyType.DYNAMIC;
    }

    private void checkDataValidity() {
        assertPresent(pos, "Position");
        assertPresent(dimension, "Size");
    }

    private void assertPresent(final Optional<?> opt, final String varName) {
        if (!opt.isPresent()) {
            throw new IllegalStateException(varName + " must be specified");
        }
    }
}
