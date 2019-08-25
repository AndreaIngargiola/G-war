package model.components;

import org.jbox2d.common.Vec2;

import enumerators.HorizontalDirection;
import model.GameModelImpl;
import model.events.PunchEvent;

/**
 * An implementation of the interface {@link Punch}.
 * It uses the raycast to see if he hit the enemy.
 */
public class PunchImpl extends AbstractEntityComponent implements Punch {

    private static final int POINTS = 20;
    private static final int RANGE = 10;
    private final FindEnemy eyes = new FindEnemy(); 

    @Override
    public final void punch() {
    	eyes.setHit(false);
        if (this.getEntity().get(Movement.class).getFaceDirection().equals(HorizontalDirection.RIGHT)) {
        	GameModelImpl.getWorld().raycast(eyes, this.getEntity().getCenter(), 
                                    this.getEntity().getCenter().add(new Vec2(RANGE, 0)));
        } else {
        	GameModelImpl.getWorld().raycast(eyes, this.getEntity().getCenter(), 
                                     this.getEntity().getCenter().add(new Vec2(-RANGE, 0)));
        }
        if (eyes.isHit()) {
            this.getEntity().get(Points.class).addPoints(POINTS);
        }
        this.getEntity().post(new PunchEvent(this.getEntity()));
    }
}
