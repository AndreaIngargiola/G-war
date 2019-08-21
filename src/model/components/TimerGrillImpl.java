package model.components;

import org.jbox2d.common.Vec2;

//import test.Main;
import enumerators.EntityState;
import model.engine.GameModelImpl;
import model.events.ChangeStateEvent;

/**
 * Implementation class for the interface {@link TimerGrill} .
 */
public class TimerGrillImpl extends AbstractEntityComponent implements TimerGrill {

    private static final float ADDICTIONAL_LENGTH = 5;
    private final GrillEyes eyes = new GrillEyes();
    private boolean dangerous = false;

    @Override
    public final void changeState() {
    	this.eyes.setHit(false);

        if (this.dangerous) {
            this.post(new ChangeStateEvent(this.getEntity(), EntityState.OFF));
        } else {
            this.post(new ChangeStateEvent(this.getEntity(), EntityState.ON));
            GameModelImpl.getWorld().raycast(this.eyes, 
            		                         new Vec2(this.getEntity().getRightSide(), this.getEntity().getTopSide() -  ADDICTIONAL_LENGTH), 
                                             new Vec2(this.getEntity().getLeftSide(), this.getEntity().getTopSide() -  ADDICTIONAL_LENGTH));
           if(!this.eyes.isHit()) {
                GameModelImpl.getWorld().raycast(this.eyes, 
                		                         new Vec2(this.getEntity().getLeftSide(), this.getEntity().getTopSide() -  ADDICTIONAL_LENGTH), 
                                                 new Vec2(this.getEntity().getRightSide(), this.getEntity().getTopSide() -  ADDICTIONAL_LENGTH));
           }
        }
        this.dangerous = !this.dangerous;
    }

    @Override
    public final boolean isDangerous() {
        return this.dangerous;
    }

    @Override
    public final String toString() {
        return "TimerGrill";
    }
}
