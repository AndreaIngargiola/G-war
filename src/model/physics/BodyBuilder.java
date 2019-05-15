package model.physics;

import org.jbox2d.common.Vec2;

/**
 * Builder for {@link EntityPhysic}. 
 */
public interface BodyBuilder {
    /**
     * @param pos The position of the spawn point of the entity.
     * @return The {@link BodyBuilder} instance.
    */
    BodyBuilder position(Vec2 pos);
    /**
     * A body not subject to forces will not respond to forces nor impulses and
     * won't be pushed by other bodies. This includes the normal force so the body
     * will be able to pass through others and it's especially true for collisions
     * with other bodies of the same type.
     * 
     * Default true.
     * 
     * @param isSubjectToForces true if subject to forces.
     * @return the {@link BodyBuilder} instance.
    */
    BodyBuilder subjectToForces(boolean isSubjectToForces);
    /**
     * Default true.
     * 
     * @param isMoveable If false the body won't move.
     * @return The {@link BodyBuilder} instance.
    */
    BodyBuilder moveable(boolean isMoveable);
    /**
     * Default true.
     * 
     * @param isSolid If false the body can pass through other bodies.
     * @return The {@link BodyBuilder} instance.
    */
    BodyBuilder solid(boolean isSolid);
    /**
     * 
     * @param friction The friction applied during contact with other bodies.
     * @return The {@link BodyBuilder} instance.
     */
    BodyBuilder friction(float friction);
    /**
     * 
     * @param gravity The gravity scale to apply to the body.
     * @return The {@link BodyBuilder} instance.
     */
    BodyBuilder gravity(float gravity);
    /**
     * 
     * @param density The body density to be applied.
     * @return The {@link BodyBuilder} instance.
     */
    BodyBuilder density(float density);
    /**
     *
     * @return The built {@link EntityPhysic} instance.
     */
    EntityPhysic build();
}
