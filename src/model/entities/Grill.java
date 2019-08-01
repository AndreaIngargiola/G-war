package model.entities;

import enumerators.Faction;
import model.components.ArchitectureImpl;
import model.components.TimerGrillImpl;
import model.engine.TimerTaskGrill;


/**
 * Models a Grill.
 * A Grill is a platform that turns dangerous to the player and goes back to normal every tot seconds.
 */
public final class Grill extends AbstractEntity {

    private static final Faction TYPE = Faction.PSYCO_IMMORTAL;

    /**
     * The component {@link TimerGrillImpl} handles the Grill switch from dangerous to neutral.
     * 
     * @param timerTask
     *                it contains a list of all the Grills created, so he knows witch entities must post the {@link TimerEvent}
     */
    public Grill(final TimerTaskGrill timerTask) {
        super(TYPE);
        timerTask.add(this);
        register(timerTask);
        add(new TimerGrillImpl());
        add(new ArchitectureImpl());
    }

    @Override
    public String toString() {
        return "Grill";
    }
}
