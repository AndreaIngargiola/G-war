package model.components;


import enumerators.CollisionSide;
import model.events.CollisionEvent;
import model.events.Damage;
import model.events.Death;
import model.events.PointsEvent;

/**
 * Implementation class for the interface {@link CollisionHandler} .
 */

public class CollisionHandlerImpl extends AbstractEntityComponent implements CollisionHandler {

    @Override
    public final void collisionListener(final CollisionEvent collision) {

    final int MOLTIPLIER_FACTOR = 20;
    switch (collision.getOther().getType()) {

        case PSYCO_MORTAL:
            if (collision.getSide() == CollisionSide.SIDE || collision.getSide() == CollisionSide.TOP) {
                collision.getSource().post(new Damage(collision.getSource(), collision.getOther().get(Attack.class).getDamage()));
            } else {
                collision.getOther().post(new Damage(collision.getOther(), collision.getSource().get(Attack.class).getDamage()));
                collision.getSource().post(new PointsEvent(collision.getSource(), collision.getOther().get(Attack.class).getDamage() * MOLTIPLIER_FACTOR));
            }
            break;

        case PSYCO_IMMORTAL:
            if (collision.getOther().toString() == "Grill") {
                if (collision.getOther().get(TimerGrillImpl.class).getIsDangerous()) {
                    collision.getSource().post(new Damage(collision.getSource(), collision.getOther().get(Attack.class).getDamage()));
                } 
            } else {
                collision.getSource().post(new Death(collision.getSource()));
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
