package model.entities;

import enumerators.Faction;
import model.components.AttackImpl;
import model.components.CollisionHandlerImpl;
import model.components.LifeImpl;
import model.components.PointsImpl;

/**
 * Models the player.
 */

public final class Player extends AbstractEntity {

    private static final Faction TYPE = Faction.NEUTRAL_MORTAL;
    private static final int DEFAULT_HEALTH = 10;
    private static final int DEFAULT_ATTACK = 1;

    /**
     * 
     */
    public Player() {
        super(TYPE);
        add(new LifeImpl(DEFAULT_HEALTH));
        add(new AttackImpl(DEFAULT_ATTACK));
        add(new CollisionHandlerImpl());
        add(new PointsImpl());
    }

    @Override
    public String toString() {
        return "Player";
    }
}
