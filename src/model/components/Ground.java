package model.components;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;

/**
 * An implementation of the interface {@link RayCastCallback}.
 * It is used by the coward to see if he is on the edge of a platform or not
 * and by the player to see if he is on the ground when he has to jump.
 */
public class Ground implements RayCastCallback {

    private boolean hit;

    @Override
    public final float reportFixture(final Fixture fixure, final Vec2 arg1, final Vec2 arg2, final float arg3) {
        hit = true;
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
