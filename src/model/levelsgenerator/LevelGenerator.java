package model.levelsgenerator;

import java.awt.Point;
import java.util.Map;

/**
 * Is the main class for the level generation engine.
 */
public interface LevelGenerator {

    /**
     * Get a generated list of spawn points associated with an entity to spawn.
     * @return the list of spawn points associated with an entity to spawn.
     */
    Map<Point, String> getNewLevel();

    /**
     * Get the current iteration of the level generator.
     * @return an int with the current iteration of the level generator.
     */
    int getIteration();
}
