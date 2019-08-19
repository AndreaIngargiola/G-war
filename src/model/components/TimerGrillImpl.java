package model.components;

import org.jbox2d.common.Vec2;

import Test.Main;
import enumerators.EntityState;
import model.events.ChangeStateEvent;

/**
 * Implementation class for the interface {@link TimerGrill} .
 */
public class TimerGrillImpl extends AbstractEntityComponent implements TimerGrill {

    private static final float ADDICTIONAL_LENGTH = 5;
    private final GrillEyes eyes = new GrillEyes();
    private boolean isDangerous = false;

    /**
     * Every TIME time chance the boolean isDangerour from true to false or vice versa.
     */
    public TimerGrillImpl() {
        super();
    }

    @Override
    public final void changeState() {
        if (this.isDangerous) {
            this.isDangerous = false;
            this.post(new ChangeStateEvent(this.getEntity(), EntityState.OFF));
        } else {
            this.isDangerous = true;
            this.post(new ChangeStateEvent(this.getEntity(), EntityState.ON));
            Main.getWorld().raycast(this.eyes, new Vec2(this.getEntity().getRightSide(), this.getEntity().getTopSide() -  ADDICTIONAL_LENGTH), 
                    new Vec2(this.getEntity().getLeftSide(), this.getEntity().getTopSide() -  ADDICTIONAL_LENGTH));
            Main.getWorld().raycast(this.eyes, new Vec2(this.getEntity().getLeftSide(), this.getEntity().getTopSide() -  ADDICTIONAL_LENGTH), 
                    new Vec2(this.getEntity().getRightSide(), this.getEntity().getTopSide() -  ADDICTIONAL_LENGTH));
        }
    }

    @Override
    public final boolean getIsDangerous() {
        return this.isDangerous;
    }

    @Override
    public final String toString() {
        return "TimerGrill";
    }
}
