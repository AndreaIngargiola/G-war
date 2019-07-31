package model.entities;

import enumerators.Faction;
import model.components.AttackImpl;
import model.components.LifeImpl;
import model.components.TimerJumpImpl;

/**
 * Models the enemy Stalker.
 * He jumps every tot time and follow the player.
 */
public final class Stalker extends AbstractEntity {

    private static final Faction TYPE = Faction.PSYCO_MORTAL;

    /**
     * @param health
     *            Health level to begin with.
     * @param damage
     *            the damage he inflict when attack
     */
    public Stalker(final int health, final int damage) {
        super(TYPE);
        add(new LifeImpl(health));
        add(new AttackImpl(damage));
        add(new TimerJumpImpl());
    }

    @Override
    public String toString() {
        return "Stalker";
    }

}
