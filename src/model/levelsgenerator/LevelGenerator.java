package model.levelsgenerator;

import java.awt.Point;
import java.util.Map;

import model.entities.AbstractEntity;

/**
 * is the main class for the level generation engine.
 */
public interface LevelGenerator {

    /**
     * get a generated list of spawn points associated with an entity to spawn.
     * @return the list of spawn points associated with an entity to spawn.
     */
    Map<Point, ? extends AbstractEntity> getNewLevel();
}
