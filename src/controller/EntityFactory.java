package controller;

import org.jbox2d.common.Vec2;

/**
 * An interface for the entities creation.
 *
 */
public interface EntityFactory {

    /**
     * Create a player entity.
     * 
     * @param position
     *             the position
     * @return
     *        the related {@link EntityController}
     */
    EntityController createPlayer(Vec2 position);

    /**
     * Create a coward entity.
     * 
     * @param position
     *             the position
     * @return
     *         the related {@link EntityController}
     */
    EntityController createCoward(Vec2 position);

    /**
     * Create a platform entity.
     * 
     * @param position
     *             the position
     * @return
     *       the related {@link EntityController}
     */
    EntityController createPlatform(Vec2 position);

    /**
     * Create a grill entity.
     * 
     * @param position
     *             the position
     * @return
     *        the related {@link EntityController}
     */
    EntityController createGrill(Vec2 position);

    /**
     * Create a floor entity.
     * 
     * @param position
     *             the position
     * @return
     *        the related {@link EntityController}
     */
    EntityController createFloor(Vec2 position);

}
