package model.entities;

import org.jbox2d.common.Vec2;

import enumerators.Faction;
import model.components.AttackImpl;
import model.components.CollisionHandlerImpl;
import model.components.LifeImpl;
import model.components.MovementImpl;
import model.components.PointsImpl;
import model.components.PunchImpl;
import model.physics.BodyBuilder;

/**
 * Models the player.
 */

public final class Player extends AbstractEntity {

    private static final Faction TYPE = Faction.NEUTRAL_MORTAL;
    private static final int DEFAULT_HEALTH = 10;
    private static final int DEFAULT_ATTACK = 1;
    private static final Vec2 SIZE = new Vec2(9, 9);
    private static final float DEFAULT_WALK_SPEED = 5;
    private static final float DEFAULT_JUMP_SPEED = 55f;
    /**
     * Used for the importation of the entity by reflection.
     */
    public static final String COMPONENTS_LEGACY = "Life-Attack-Movement-CollisionHandler-Points-Punch";

    /**
     * 
     * @param bodyBuilder
     *              the related {@link BodyBuilder} object
     * @param position
     *              its position
     */
    public Player(final BodyBuilder bodyBuilder, final Vec2 position) {
        super(TYPE, bodyBuilder
                .setPosition(position)
                .setSize(SIZE)
                .build());
        add(new LifeImpl(DEFAULT_HEALTH));
        add(new AttackImpl(DEFAULT_ATTACK));
        add(new CollisionHandlerImpl(DEFAULT_WALK_SPEED));
        add(new PointsImpl());
        add(new PunchImpl());
       add(new MovementImpl(DEFAULT_WALK_SPEED, DEFAULT_JUMP_SPEED));
    }

    @Override
    public String toString() {
        return "Player";
    }
}
