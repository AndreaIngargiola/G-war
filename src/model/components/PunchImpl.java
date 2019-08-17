package model.components;

import org.jbox2d.common.Vec2;

import Test.Main;
import enumerators.HorizontalDirection;
import model.events.PunchEvent;

/**
 * An implementation of the interface {@link Punch}.
 * It uses the raycast to see if he hit the enemy.
 */
public class PunchImpl extends AbstractEntityComponent implements Punch {

    private final FindEnemy eyes = new FindEnemy(this.getEntity()); 
    private final int RANGE = 100;

    @Override
    public final void punchListener(final PunchEvent punch) {
        if (this.getEntity().get(Movement.class).getFaceDirection().equals(HorizontalDirection.RIGHT)) {
            Main.getWorld().raycast(eyes, this.getEntity().getCenter(), 
                                    this.getEntity().getCenter().add(new Vec2(RANGE, 0)));
        } else {
            Main.getWorld().raycast(eyes, this.getEntity().getCenter(), 
                                     this.getEntity().getCenter().add(new Vec2(-RANGE, 0)));
        }
    }
}
