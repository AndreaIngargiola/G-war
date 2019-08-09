package model.levelsgenerator;

import model.levelsgenerator.geometry.Grid;

/**
 * An interface for the placing of architectural elements in a level.
 */
public interface ArchitectureBuilder {

    /**
     * Given a grid, build the architecture inside it.
     * @param grid is the empty grid in which build the architecture.
     * @return a grid of the same type of the input full of architectural elements.
     */
    Grid getArchitecture(Grid grid);
}
