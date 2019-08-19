package model.components;

import org.jbox2d.common.Vec2;

import Test.Main;
import enumerators.HorizontalDirection;
import model.events.ChangeDirectionEvent;

/**
 * Models the movement component of a enemy who changes direction at the end of the platform (using raycast).
 *
 */
public class ChangeDirectionImpl extends AbstractMovement {

    private static final float ADDICTIONAL_LENGTH = 5;
    private boolean hit = true;

    /**
     * 
     * @param walkSpeed
     *                the walking speed 
     */
    public ChangeDirectionImpl(final float walkSpeed) {
        super(walkSpeed);
    }

    @Override
    public final void move() {
        final Vec2 vel = this.getEntity().getBody().getLinearVelocity();
        if (this.getFaceDirection().equals(HorizontalDirection.RIGHT)) {
            this.setLinearVelocity(vel.x + this.getWalkSpeed(), vel.y);

            Main.getWorld().raycast(this.getRayCast(), new Vec2(this.getEntity().getCenter().x, this.getEntity().getCenter().y), 
                     new Vec2(this.getEntity().getRightSide(), this.getEntity().getBottomSide() +  ADDICTIONAL_LENGTH));
        } else {
            this.setLinearVelocity(vel.x - this.getWalkSpeed(), vel.y);
            this.getEntity().getBody().getBody().setTransform(this.getEntity().getBody().getPosition().add(new Vec2((float) -0.5, 0)), 0);

            Main.getWorld().raycast(this.getRayCast(), new Vec2(this.getEntity().getCenter().x, this.getEntity().getCenter().y), 
                    new Vec2(this.getEntity().getLeftSide(), this.getEntity().getBottomSide() +  ADDICTIONAL_LENGTH));
        }

        this.hit = this.getRayCast().getHit();
        if (!hit) {
            changeDirection();
        }
        this.getRayCast().setHit(false);
    }

    @Override
    public final void update(final double dt) {
        move();
    }

    @Override
    public final void changeDirection() {
        if (this.getFaceDirection().equals(HorizontalDirection.LEFT)) {
            this.setLinearVelocity(0f, 0f);
            this.setFaceDirection(HorizontalDirection.RIGHT);
            this.getEntity().post(new ChangeDirectionEvent(this.getEntity(), HorizontalDirection.RIGHT));
        } else {
            this.setLinearVelocity(0f, 0f);
            this.setFaceDirection(HorizontalDirection.LEFT);
            this.getEntity().post(new ChangeDirectionEvent(this.getEntity(), HorizontalDirection.LEFT));
        }
    }
}
