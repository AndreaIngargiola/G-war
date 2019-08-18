package model.entities;

import org.jbox2d.common.Vec2;

import Test.Main;
import enumerators.Faction;
import model.components.ArchitectureImpl;
import model.components.TimerGrillImpl;
import model.physics.BodyBuilder;


/**
 * Models a Grill.
 * A Grill is a platform that turns dangerous to the player and goes back to normal every tot seconds.
 */
public final class Grill extends AbstractEntity {

    private static final Faction TYPE = Faction.PSYCO_IMMORTAL;
    private static final Vec2 SIZE = new Vec2(10, 10);
    /**
     * Used for the importation of the entity by reflection.
     */
    public static final String COMPONENTS_LEGACY = "Architecture";

    /**
     * The component {@link TimerGrillImpl} handles the Grill switch from dangerous to neutral.
     * 
     * @param bodyBuilder
     *              the related {@link BodyBuilder} object
     * @param position
     *              its position
     */
    public Grill(final BodyBuilder bodyBuilder, final Vec2 position) {
        super(TYPE, bodyBuilder
                .setPosition(position)
                .setSize(SIZE)
                .setIsMoveable(false)
                .setSubjectToForces(false)
                .build());
        Main.getTimer().add(this);
        register(Main.getTimer());
        add(new TimerGrillImpl());
        add(new ArchitectureImpl());
    }

    @Override
    public String toString() {
        return "Grill";
    }
}
