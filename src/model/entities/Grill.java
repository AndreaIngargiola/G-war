package model.entities;

import org.jbox2d.common.Vec2;

import enumerators.Faction;
import model.components.ArchitectureImpl;
import model.components.TimerGrillImpl;
import model.engine.TimerTaskGrill;
import model.physics.BodyBuilder;


/**
 * Models a Grill.
 * A Grill is a platform that turns dangerous to the player and goes back to normal every tot seconds.
 */
public final class Grill extends AbstractEntity {

    private static final Faction TYPE = Faction.PSYCO_IMMORTAL;
    private static final Vec2 SIZE = new Vec2(50, 50);

    /**
     * The component {@link TimerGrillImpl} handles the Grill switch from dangerous to neutral.
     * 
     * @param bodyBuilder
     *              the related {@link BodyBuilder} object
     * @param position
     *              its positioN
     * @param timerTask
     *                it contains a list of all the Grills created, 
     *                so he knows witch entities must post the {@link TimerEvent}
     */
    public Grill(final BodyBuilder bodyBuilder, final Vec2 position, final TimerTaskGrill timerTask) {
        super(TYPE, bodyBuilder
                .setPosition(position)
                .setSize(SIZE)
                .setIsMoveable(false)
                .build());
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
