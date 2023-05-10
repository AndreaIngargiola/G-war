package model.physics;

import org.jbox2d.common.Vec2;

import model.components.EntityBody;

/**
 * Models a body builder for the entities. 
 *
 */
public interface BodyBuilder {

    /**
     * @param position
     *            the position  of the BodyBuilder
     * @return the {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setPosition(Vec2 position);

    /**
     * @param size
     *            the size of the BodyBuilder.
     * @return the {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setSize(Vec2 size);

    /**
     * A BodyBuilder not subject to forces will not respond to forces and impulses and won't be pushed by other bodies.
     * 
     * Defaults to true.
     * 
     * @param opt
     *           set to false to disable forces for this BodyBuilder.
     * @return the {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setSubjectToForces(boolean opt);


    /**
     * Defaults to true.
     * 
     * @param moveable
     *            set to false if the BodyBuilder won't move from its position.
     * @return the {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setIsMoveable(boolean moveable);

    /**
     * @return the built {@link EntityBodyBuilder} instance.
     */
    EntityBody build();
}
