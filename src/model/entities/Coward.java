package model.entities;

import org.jbox2d.common.Vec2;

import enumerators.Faction;
import model.components.AttackImpl;
import model.components.ChangeDirectionImpl;
import model.components.LifeImpl;
import model.physics.BodyBuilder;

/**
 * Models the enemy Coward.
 * He doesn't follow the player but goes his way and change direction when he cannot go ahead anymore.
 */

public final class Coward extends Enemy {

    private static final Faction TYPE = Faction.PSYCO_MORTAL;
    private static final int DEFAULT_HEALTH = 1;
    private static final int DEFAULT_ATTACK = 1;
    private static final Vec2 SIZE = new Vec2(51, 51);
    private static final float DEFAULT_WALK_SPEED = 1;
    private static final float DEFAULT_JUMP_SPEED = -100;

    /**
     * 
     * @param bodyBuilder
     *              the related {@link BodyBuilder} object
     * @param position
     *              its position
     */
    public Coward(final BodyBuilder bodyBuilder, final Vec2 position) {
        super(TYPE, bodyBuilder
                .setPosition(position)
                .setSize(SIZE)
                .build());
        add(new LifeImpl(DEFAULT_HEALTH));
        add(new AttackImpl(DEFAULT_ATTACK));
        add(new ChangeDirectionImpl(DEFAULT_WALK_SPEED, DEFAULT_JUMP_SPEED));
        }

    @Override
    public String toString() {
        return "Coward";
    }
}
