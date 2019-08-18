package model.entities;

import org.jbox2d.common.Vec2;

import enumerators.Faction;
import model.components.ArchitectureImpl;
import model.physics.BodyBuilder;

/**
 * Models a Platform.
 */
public final class Platform extends AbstractEntity {

    private static final Faction TYPE = Faction.NEUTRAL_IMMORTAL;
    private static final Vec2 SIZE = new Vec2(10, 10);
    /**
     * Used for the importation of the entity by reflection.
     */
    public static final String COMPONENTS_LEGACY = "Architecture";

    /**
     * 
     * @param bodyBuilder
     *              the related {@link BodyBuilder} object
     * @param position
     *              its position
     */
    public Platform(final BodyBuilder bodyBuilder, final Vec2 position) {
        super(TYPE, bodyBuilder
                .setPosition(position)
                .setSize(SIZE)
                .setIsMoveable(false)
                .setSubjectToForces(false)
                .build());
        add(new ArchitectureImpl());
    }

    @Override
    public String toString() {
        return "Platform";
    }
}
