package model.entities;

import enumerators.Faction;
import model.components.AttackImpl;
import model.components.LifeImpl;

/**
 * Models the enemy Coward.
 * He doesn't follow the player but goes his way and change direction when he cannot go ahead anymore.
 */

public final class Coward extends AbstractEntity {

    private static final Faction TYPE = Faction.PSYCO_MORTAL;
    private static final int DEFAULT_HEALTH = 1;
    private static final int DEFAULT_ATTACK = 1;
    /**
     *
     */
    public Coward() {
        super(TYPE);
        add(new LifeImpl(DEFAULT_HEALTH));
        add(new AttackImpl(DEFAULT_ATTACK));
    }

    @Override
    public String toString() {
        return "Coward";
    }
}
