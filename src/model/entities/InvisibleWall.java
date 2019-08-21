package model.entities;

import org.jbox2d.common.Vec2;

import enumerators.Faction;
import model.physics.BodyBuilder;

@LevelGenerationIgnore
public final class InvisibleWall extends AbstractEntity {

    private static final Faction TYPE = Faction.NEUTRAL_IMMORTAL;
    private static final Vec2 SIZE = new Vec2(10, 400);
    /**
     * Used for the importation of the entity by reflection.
     */

    /**
     * 
     * @param bodyBuilder
     *              the related {@link BodyBuilder} object
     * @param position
     *              its position
     */
    public InvisibleWall(final BodyBuilder bodyBuilder, final Vec2 position) {
        super(TYPE, bodyBuilder
                .setPosition(position)
                .setSize(SIZE)
                .setIsMoveable(false)
                .setSubjectToForces(false)
                .build());
    }

    @Override
    public String toString() {
        return "InvisibleWall";
    }

}
