package model.components;

import org.jbox2d.common.Vec2;

import enumerators.CollisionSide;
import model.entities.Entity;
import model.events.CollisionEvent;

/**
 * Implementation class for the interface {@link CollisionHandler} .
 */

public class CollisionHandlerImpl extends AbstractEntityComponent implements CollisionHandler {

    private static final int LIFE_PENALITY = 10;
    private static final int POINTS = 20;
    private final float jumpSpeed;


    /**
     * 
     * @param jumpSpeed
     *               the jumping speed
     */
    public CollisionHandlerImpl(final float jumpSpeed) {
        super();
        this.jumpSpeed = jumpSpeed;
    }



    @Override
    public final void collision(final Entity source, final Entity other, final CollisionSide side) {

       switch (other.getType()) {

           case PSYCO_MORTAL:
               if (side == CollisionSide.OTHERS) {
                   this.getEntity().get(Life.class).damage(other.get(Attack.class).getDamage());
                   other.get(Movement.class).changeDirection();
                   post(new CollisionEvent(this.getEntity()));
               } else {
                   this.getEntity().get(Points.class).addPoints(POINTS);
                   other.post(new CollisionEvent(other));
                   other.get(Life.class).damage(source.get(Attack.class).getDamage());
                   this.getEntity().getBody().applyImpulse(new Vec2(0, jumpSpeed));
               }
               break;

           case PSYCO_IMMORTAL:
               if (other.toString().equals("Grill")) {
                   if (other.get(TimerGrill.class).isDangerous()) {
                       post(new CollisionEvent(this.getEntity()));
                       this.getEntity().get(Life.class).damage(other.get(Attack.class).getDamage());
                   }
               } else {
                   post(new CollisionEvent(this.getEntity()));
                   this.getEntity().get(Life.class).damage(LIFE_PENALITY);
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
