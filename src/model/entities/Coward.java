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
    /**
     * @param health
     *            Health level to begin with.
     * @param damage
     *            the damage he inflict when attack
     */
    public Coward(final int health, final int damage) {
        super(TYPE);
        add(new LifeImpl(health));
        add(new AttackImpl(damage));
    }

    @Override
    public String toString() {
        return "Coward";
    }
}
