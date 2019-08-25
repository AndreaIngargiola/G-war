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

	private boolean hit;
    
    @Override
    public final float reportFixture(final Fixture fixture, final Vec2 arg1, final Vec2 arg2, final float arg3) {

        final Entity entity = (Entity) fixture.getBody().getUserData();
        if (entity.getType().equals(Faction.NEUTRAL_MORTAL)) {
            entity.post(new CollisionEvent(entity));
            if(entity.getComponents().getInterfaces().contains(Life.class)) {
                entity.get(Life.class).damage(1);
                this.hit = true;
            }
        }
        return 0;
    }

    /**
     * 
     * @return hit
     */
    public final boolean isHit() {
        return hit;
    }

    /**
     * 
     * @param hit
     *         true if the raycast met a body
     */
    public final void setHit(final boolean hit) {
        this.hit = hit;
    }
}
