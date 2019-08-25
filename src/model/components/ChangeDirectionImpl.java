package model.components;

import org.jbox2d.common.Vec2;

import enumerators.HorizontalDirection;
import model.GameModelImpl;
import model.events.ChangeDirectionEvent;

/**
 * Models the movement component of a enemy who changes direction at the end of the platform (using raycast).
 *
 */
public class ChangeDirectionImpl extends AbstractMovement {

    private static final float ADDICTIONAL_Y = 5;
    private static final float ADDICTIONAL_X = 1;

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
        this.getRayCast().setHit(false);
        
        if (this.getFaceDirection().equals(HorizontalDirection.RIGHT)) {
            this.setLinearVelocity(this.getWalkSpeed(), vel.y);

            GameModelImpl.getWorld().raycast(this.getRayCast(), new Vec2(this.getEntity().getRightSide(), this.getEntity().getCenter().y), 
                     new Vec2(this.getEntity().getRightSide(), this.getEntity().getBottomSide() +  ADDICTIONAL_Y));
            
            GameModelImpl.getWorld().raycast(this.getRayCast(), new Vec2(this.getEntity().getRightSide() + ADDICTIONAL_X, this.getEntity().getCenter().y), 
                    new Vec2(this.getEntity().getRightSide() + ADDICTIONAL_X, this.getEntity().getBottomSide() +  ADDICTIONAL_Y));
        } else {
            this.setLinearVelocity(- this.getWalkSpeed(), vel.y);
            this.getEntity().getBody().getBody().setTransform(this.getEntity().getBody().getPosition().add(new Vec2((float) -0.5, 0)), 0);

            GameModelImpl.getWorld().raycast(this.getRayCast(), new Vec2(this.getEntity().getLeftSide(), this.getEntity().getCenter().y), 
                    new Vec2(this.getEntity().getLeftSide(), this.getEntity().getBottomSide() +  ADDICTIONAL_Y));
            
            GameModelImpl.getWorld().raycast(this.getRayCast(), new Vec2(this.getEntity().getLeftSide() - ADDICTIONAL_X, this.getEntity().getCenter().y), 
                    new Vec2(this.getEntity().getLeftSide() - ADDICTIONAL_X, this.getEntity().getBottomSide() +  ADDICTIONAL_Y));
        }

        
        if (!this.getRayCast().isHit()) {
            changeDirection();
        }
    }

    @Override
    public final void update(final double dt) {
        move();
    }

    @Override
    public final void changeDirection() {
        if (this.getFaceDirection().equals(HorizontalDirection.LEFT)) {
            this.setLinearVelocity(0f, this.getLinearVelocity().y);
            this.setFaceDirection(HorizontalDirection.RIGHT);
            this.getEntity().post(new ChangeDirectionEvent(this.getEntity(), HorizontalDirection.RIGHT));
        } else {
            this.setLinearVelocity(0f, this.getLinearVelocity().y);
            this.setFaceDirection(HorizontalDirection.LEFT);
            this.getEntity().post(new ChangeDirectionEvent(this.getEntity(), HorizontalDirection.LEFT));
        }
    }
}
