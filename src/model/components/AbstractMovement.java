package model.components;

import org.jbox2d.common.Vec2;

import enumerators.HorizontalDirection;

/**
 *  Represents an abstract implementation of the base interface {@link Movement}.
 */
public abstract class AbstractMovement extends AbstractEntityComponent implements Movement {

    private HorizontalDirection faceDirection = HorizontalDirection.RIGHT;
    private final  float walkSpeed;
    private final float jumpSpeed;
    private final Ground ground = new Ground();

    /**
     * 
     * @param walkSpeed
     *                the walking speed 
     */
    public AbstractMovement(final float walkSpeed) {
        super();
        this.walkSpeed = walkSpeed;
        this.jumpSpeed = 0f;
    }
    /**
     * 
     * @param walkSpeed
     *                the walking speed 
     * @param jumpSpeed
     *                the jumping speed
     */
    public AbstractMovement(final float walkSpeed, final float jumpSpeed) {
        super();
        this.walkSpeed = walkSpeed;
        this.jumpSpeed = jumpSpeed;
    }

    @Override
    public final float getWalkSpeed() {
        return walkSpeed;
    }

    @Override
    public final float getJumpSpeed() {
        return jumpSpeed;
    }

    @Override
    public final void stop() {
        setLinearVelocity(0f, getLinearVelocity().y);
    }

    /**
     * @return The direction
     *                (RIGHT or LEFT)
     */
    @Override
    public final HorizontalDirection getFaceDirection() {
        return faceDirection;
    }

    @Override
    public void move() {
    }

    @Override
    public void move(final Vec2 movement) {
    }

    @Override
    public void changeDirection() {
    }

    /**
     * @allowed if the extended class does not utilize it, keep returning false, implement the method otherwise.
     */
    @Override
    public boolean isOnGround() {
        return false;
    }

    /**
     * 
     * @param direction
     *              (RIGHT or LEFT)
     */
    protected final void setFaceDirection(final HorizontalDirection direction) {
        this.faceDirection = direction;
    }


    /**
     * 
     * @return {@link RayCastCallback}
     */
    protected final Ground getRayCast() {
        return ground;
    }

    /**
     * 
     * @param x
     *        the x velocity
     * @param y
     *        the y velocity
     */
    protected final void setLinearVelocity(final float x, final float y) {
        this.getEntity().getBody().setLinearVelocity(new Vec2(x, y));
    }

    /**
     * 
     * @return the linear velocity
     */
    protected final Vec2 getLinearVelocity() {
        return this.getEntity().getBody().getLinearVelocity();
    }

}
