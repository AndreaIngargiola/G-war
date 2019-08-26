package model.components;

import org.jbox2d.common.Vec2;

import enumerators.HorizontalDirection;
import model.GameModelImpl;
import model.events.ChangeDirectionEvent;
import model.events.JumpEvent;

/**
 * Models the movement component of the player.
 *
 */
public class MovementImpl extends AbstractMovement {

    private static final float FRICTION = 0.2f;
    private static final float ADDICTIONAL_LENGTH = 0.6f;

    /**
     * 
     * @param walkSpeed
     *           the walking speed
     * @param jumpSpeed
     *           the jumping speed
     */
    public MovementImpl(final float walkSpeed, final float jumpSpeed) {
        super(walkSpeed, jumpSpeed);
    }

    @Override
    public final void move(final Vec2 movement) {
        final Vec2 vel = this.getEntity().getBody().getLinearVelocity();

        if (this.isOnGround()) {

            if (movement.y == 1) {
                this.setLinearVelocity(vel.x, vel.y - this.getJumpSpeed());
                post(new JumpEvent(this.getEntity()));
            }

            if (movement.x == 1) {
                if (this.getFaceDirection().equals(HorizontalDirection.LEFT)) {
                    this.setFaceDirection(HorizontalDirection.RIGHT);
                    this.getEntity().post(new ChangeDirectionEvent(this.getEntity(), HorizontalDirection.RIGHT));
                }
                this.setLinearVelocity(vel.x + this.getWalkSpeed(), vel.y);
            }

            if (movement.x == -1) {
                if (this.getFaceDirection().equals(HorizontalDirection.RIGHT)) {
                    this.setFaceDirection(HorizontalDirection.LEFT);
                    this.getEntity().post(new ChangeDirectionEvent(this.getEntity(), HorizontalDirection.LEFT));
                }
                this.setLinearVelocity(vel.x - this.getWalkSpeed(), vel.y);
           }
       } else {

            if (movement.x == 1) {
                if (this.getFaceDirection().equals(HorizontalDirection.LEFT)) {
                    this.setFaceDirection(HorizontalDirection.RIGHT);
                    this.getEntity().post(new ChangeDirectionEvent(this.getEntity(), HorizontalDirection.RIGHT));
                }
                this.setLinearVelocity(vel.x + this.getWalkSpeed() * FRICTION, vel.y);
            }

            if (movement.x == -1) {
                if (this.getFaceDirection().equals(HorizontalDirection.RIGHT)) {
                    this.setFaceDirection(HorizontalDirection.LEFT);
                    this.getEntity().post(new ChangeDirectionEvent(this.getEntity(), HorizontalDirection.LEFT));
                }
                this.setLinearVelocity(vel.x - this.getWalkSpeed() * FRICTION, vel.y);
            }
        }
    }
    
    @Override
    public boolean isOnGround() {
        this.getRayCast().setHit(false);

        GameModelImpl.getWorld().raycast(this.getRayCast(), new Vec2(this.getEntity().getCenter().x, this.getEntity().getCenter().y), 
                new Vec2(this.getEntity().getCenter().x, this.getEntity().getBottomSide() +  ADDICTIONAL_LENGTH));
        
        return this.getRayCast().isHit();
    }

}
