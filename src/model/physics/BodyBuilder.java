package model.physics;

import org.jbox2d.common.Vec2;

import model.components.EntityBody;



public interface BodyBuilder {
	/**
     * @param position
     *            The position of the center of the BodyBuilder
     * @return The {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setPosition(Vec2 position);

    /**
     * @param size
     *            Size of the BodyBuilder bounding box.
     * @return The {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setSize(Vec2 size);

    /**
     * A BodyBuilder not subject to forces will not respond to forces nor impulses and won't be pushed by other bodies.
     * This includes the normal force so the BodyBuilder will be able to pass through others and it's especially
     * true for collisions with other bodies of the same type and / or unmoveable ones.
     * 
     * Defaults to true.
     * 
     * @param opt
     *            Set to false to disable forces for this BodyBuilder.
     * @return The {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setSubjectToForces(boolean opt);

    /**
     * Defaults to true.
     * 
     * @param isSolid
     *            A non-solid BodyBuilder can pass through other bodies and vice-versa.
     * @return The {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setIsSolid(boolean isSolid);

    /**
     * Defaults to true.
     * 
     * @param moveable
     *            If false the BodyBuilder won't move from its position.
     * @return The {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setIsMoveable(boolean moveable);

    /**
     * @param friction
     *            The friction applied during contact with other bodies.
     * @return The {@link BodyBuilderBuilder} instance.
     */
    BodyBuilder setFriction(double friction);

    /**
     * @return The built {@link EntityBodyBuilder} instance.
     */
    EntityBody build();
}
