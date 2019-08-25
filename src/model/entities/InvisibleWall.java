package model.entities;

import org.jbox2d.common.Vec2;

import enumerators.Faction;
import model.physics.BodyBuilder;

/**
 * An invisible wall for forbid out of bounds positions to the player.
 * This entity will be placed directly in the game model and not via level generator because is not a gameplay-wise entity.
 */
@LevelGenerationIgnore
public final class InvisibleWall extends AbstractEntity {

    private static final Faction TYPE = Faction.NEUTRAL_IMMORTAL;
    private static final Vec2 SIZE = new Vec2(10, 400);

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
