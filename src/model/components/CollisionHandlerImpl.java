package model.components;


import org.jbox2d.common.Vec2;

import enumerators.CollisionSide;
import model.events.CollisionEvent;
import model.events.Death;


/**
 * Implementation class for the interface {@link CollisionHandler} .
 */

public class CollisionHandlerImpl extends AbstractEntityComponent implements CollisionHandler {

    private final float jumpSpeed;
    private final int MOLTIPLIER_FACTOR = 20;
    /**
     * 
     * @param jumpSpeed
     *               the jumping speed
     */
    public CollisionHandlerImpl(final float jumpSpeed) {
        this.jumpSpeed = jumpSpeed;
    }

    @Override
    public final void collisionListener(final CollisionEvent collision) {

       switch (collision.getOther().getType()) {

           case PSYCO_MORTAL:
               if (collision.getSide() == CollisionSide.OTHERS) {
                   this.getEntity().get(Life.class).demage(collision.getOther().get(Attack.class).getDamage());
               } else {
                   collision.getOther().get(Life.class).demage(collision.getSource().get(Attack.class).getDamage());
                   this.getEntity().get(Points.class).addPoints(collision.getOther().get(Attack.class).getDamage() * MOLTIPLIER_FACTOR);
                   this.getEntity().getBody().applyImpulse(new Vec2(0, jumpSpeed));
               }
               break;

           case PSYCO_IMMORTAL:
               if (collision.getOther().toString() == "Grill") {
                   if (collision.getOther().get(TimerGrillImpl.class).getIsDangerous()) {
                       this.getEntity().get(Life.class).demage(collision.getOther().get(Attack.class).getDamage());
                   } 
               } else {
                   post(new Death(getEntity()));
               }
               break;

           default:
               break;
           }
    }

    @Override
    public final String toString() {
        return "CollisionHandler";
    }
}
