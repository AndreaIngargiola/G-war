package model.components;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

import enumerators.Faction;
import model.entities.Entity;
import model.events.CollisionEvent;

/**
 * An implementation of the interface {@link RayCastCallback}.
 * It is used to see if an enemy is close enough to the player when he punches.
 */
public class FindEnemy implements RayCastCallback {

    private boolean hit = false;

    /**
     * 
     * @return hit
     */
    public final boolean getHit() {
        return hit;
    }

    /**
     * 
     * @param hit
     *        true if the raycast met a body
     */
    public final void setHit(final boolean hit) {
        this.hit = hit;
    }

    @Override
    public final float reportFixture(final Fixture fixture, final Vec2 arg1, final Vec2 arg2, final float arg3) {
        final Entity entity = (Entity) fixture.getBody().getUserData();
            if (entity.getType().equals(Faction.PSYCO_MORTAL)) {
            entity.post(new CollisionEvent(entity));
            entity.get(Life.class).demage(4);
            this.hit = true;
        }
        return -1;
    }
}
