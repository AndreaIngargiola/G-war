package model.components;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import model.entities.Entity;

/**
 * Models our entities EntityBody.
 */
public interface EntityBody extends EntityComponent {

    /**
     * 
     * @return The position
     */
     Vec2 getPosition();

    /**
     * 
     * @return The size of the EntityBody. Entity bodies are all approximated as rectangles.
     */
    Vec2 getDimension();

    /**
     * 
     * @param velocity
     *            The velocity vector
     */
    void setLinearVelocity(Vec2 velocity);

    /**
     * 
     * @return The velocity vector
     */
    Vec2 getLinearVelocity();

    /**
     * 
     * @param impulse
     *            The impulse vector.
     */
    void applyImpulse(Vec2 impulse);

    /**
     * 
     * @param entity
     *            The entity
     */
    void setUserData(Entity entity);

    /**
     * 
     * @return the {@link Body}
     */
    Body getBody();

    /**
     * The gravity scale will be multiplied by the gravity to decide how much it should influence this EntityBody.
     * 
     * @param scale
     *            The factor.
     */

//    void setGravityScale(double scale);
//
    /**
     * Gets all {@link EntityEntityBody} objects colliding with this one.
     * 
     * @return a Stream of {@link EntityEntityBody} objects
     */
   // Stream<EntityBody> getContacts();
//
//    /**
//     * @return true if the EntityBody is solid, rigid, cannot be passed through.
//     */
//    boolean isSolid();
}
