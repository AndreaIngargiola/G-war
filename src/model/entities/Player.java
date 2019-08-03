package model.entities;

import enumerators.Faction;
import model.components.AttackImpl;
import model.components.CollisionHandlerImpl;
import model.components.LifeImpl;

/**
 * Models the player.
 */

public final class Player extends AbstractEntity {

    private static final Faction TYPE = Faction.NEUTRAL_MORTAL;
    /**
     * @param health
     *            Health level to begin with.
     * @param damage
     *            the damage he inflict when attack
     */
    public Player(final int health, final int damage) {
        super(TYPE);
        add(new LifeImpl(health));
        add(new AttackImpl(damage));
        add(new CollisionHandlerImpl());
    }

    @Override
    public String toString() {
        return "Player";
    }
}