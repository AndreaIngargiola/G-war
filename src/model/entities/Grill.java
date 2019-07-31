package model.entities;

import enumerators.Faction;
import model.components.TimerImpl;

/**
 * Models a Grill.
 * A Grill is a platform that turns dangerous to the player and goes back to normal every tot seconds.
 */
public final class Grill extends AbstractEntity {

    private static final Faction TYPE = Faction.PSYCO_IMMORTAL;

    /**
     * The component {@link TimerImpl} handles the Grill switch from dangerous to neutral.
     */
    public Grill() {
        super(TYPE);
        add(new TimerImpl());
    }

    @Override
    public String toString() {
        return "Grill";
    }
}
