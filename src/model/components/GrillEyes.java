package model.components;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import enumerators.Faction;
import model.entities.Entity;
import model.events.CollisionEvent;

/**
 * An implementation of the interface {@link RayCastCallback}.
 * It is used by the grill to see if the player is over it when it turns dangerous.
 */
public class GrillEyes implements RayCastCallback {

    @Override
    public final float reportFixture(final Fixture fixture, final Vec2 arg1, final Vec2 arg2, final float arg3) {

        final Entity entity = (Entity) fixture.getBody().getUserData();
        if (entity.getType().equals(Faction.NEUTRAL_MORTAL)) {
            entity.post(new CollisionEvent(entity));
            entity.get(Life.class).demage(10);
        }
        return 0;
    }

}
