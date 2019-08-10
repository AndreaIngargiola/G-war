package model.entities;

import org.jbox2d.common.Vec2;

import enumerators.Faction;
import model.physics.BodyBuilder;

/**
 * Models a Lake.
 * If the player fall into a lake he dies.
 */
public final class Floor extends AbstractEntity {

    private static final Faction TYPE = Faction.PSYCO_IMMORTAL;
    private static final Vec2 SIZE = new Vec2(800, 50);

    /**
     * 
     * @param bodyBuilder
     *              the related {@link BodyBuilder} object
     * @param position
     *              its position
     */
    public Floor(final BodyBuilder bodyBuilder, final Vec2 position) {
        super(TYPE, bodyBuilder
                .setPosition(position)
                .setSize(SIZE)
                .setIsMoveable(false)
                .build());
    }

    @Override
    public String toString() {
        return "Floor";
    }
}
