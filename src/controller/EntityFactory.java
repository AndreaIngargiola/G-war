package controller;

import javafx.scene.Scene;
import view.StatisticsView;

/**
 * An interface for the entities creation.
 *
 */
public interface EntityFactory {

    /**
     * Create a player entity.
     * 
     * @param scene
     *             the scene
     * @param statistics
     *             the statistics view of the player
     * @return
     *        the related {@link EntityController}
     */
    EntityController createPlayer(Scene scene, StatisticsView statistics);

    /**
     * Create a coward entity.
     * 
     * @return
     *         the related {@link EntityController}
     */
    EntityController createCoward();

    /**
     * Create a platform entity.
     * 
     * @return
     *       the related {@link EntityController}
     */
    EntityController createPlatform();

    /**
     * Create a grill entity.
     * 
     * @return
     *        the related {@link EntityController}
     */
    EntityController createGrill();

    /**
     * Create a floor entity.
     * 
     * @return
     *        the related {@link EntityController}
     */
    EntityController createFloor();

}
