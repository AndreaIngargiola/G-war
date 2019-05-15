package model.physics;

import org.jbox2d.common.Vec2;

/**
 * Models our entities body.
 */
public interface EntityPhysic {
    /**
     * 
     * @return The position.
     */
    Vec2 getPosition();
    /**
     * 
     * @return true If true the body is solid, rigid can't be passed through
     */
    boolean isSolid();
    /**
     * 
     * @param velocity The velocity vector.
     */
    void setLiearVelocity(Vec2 velocity);
    /**
     * 
     * @return The value of the velocity.
     */
    Vec2 getLinearVelocity();
    /**
     * 
     * @param scale The scale factor of influence on the body.
     */
    void setScaleOfGravity(double scale);
    /**
     * 
     * @param impulse The impulse vector.
     */
    void setImpulse(Vec2 impulse);
}
