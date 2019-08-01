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
    private static final int DEFAULT_HEALTH = 2;
    private static final int DEFAULT_ATTACK = 2;

    /**
     *
     */
    public Stalker() {
        super(TYPE);
        add(new LifeImpl(DEFAULT_HEALTH));
        add(new AttackImpl(DEFAULT_ATTACK));
        add(new TimerJumpImpl());
    }

    @Override
    public String toString() {
        return "Stalker";
    }

}
