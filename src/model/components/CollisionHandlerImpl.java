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
    
    final int playerAttack = collision.getSource().get(Attack.class).getDamage();
    final int enemyAttack = collision.getOther().get(Attack.class).getDamage();
    final int points =enemyAttack * MOLTIPLIER_FACTOR;
    
    switch (collision.getOther().getType()) {

        case PSYCO_MORTAL:
            if (collision.getSide() == CollisionSide.SIDE || collision.getSide() == CollisionSide.TOP) {
                post(new Damage(getEntity(), enemyAttack));
            } else {
            	collision.getOther().post(new Damage(collision.getOther(), playerAttack));
                post(new PointsEvent(getEntity(), points));
            }
            break;

        case PSYCO_IMMORTAL:
            if (collision.getOther().toString() == "Grill") {
                if (collision.getOther().get(TimerGrillImpl.class).getIsDangerous()) {
                    post(new Damage(getEntity(), enemyAttack));
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
