package model.levelsgenerator.geometry;

import java.util.List;
import java.util.Map;

import model.levelsgenerator.EntityBlock;
import model.levelsgenerator.LevelGenerationEntity;

/**
 * A class for the matrix generation and blocks insertion.
 *
 */
public interface Grid {

    /**
     * reinitialize the matrix to the starting status.
     */
    void reset();

    /**
     * Get the absolute matrix coordinates given a certain block shape (list of coordinates) and a origin point on the matrix. 
     * @param b is the block to overlap.
     * @param mOriginPoint is the point in the matrix that corresponds to the spawn point of the block and in which 
     * the relative coordinates of the block will be converted in absolute coordinates of the matrix.
     * @return a list of matrix coordinates that are in matrix bounds and corresponds to the points 
     * occupied by that block if is placed with that point of origin.
     */
    List<Coordinate> getOverlap(Coordinate mOriginPoint, BlockImpl b);

    /**
     * get the matrix element at elemCoordinates.
     * @param elemCoordinates are the integer coordinates of the desired element.
     * @return an Optional<Integer> of the matrix element at elemCoordinates if exist.
     */
    LevelGenerationEntity getElement(Coordinate elemCoordinates);

    /**
     * try to place the block b in the matrix with spawnPoint as its center.
     * @param mOriginPoint is the point in the matrix that corresponds to the spawn point of the block and in which 
     * the relative coordinates of the block will be converted in absolute coordinates of the matrix.
     * 
     * @param b is the block to place.
     * @return true if the block is placed, false if the placing is failed.
     */
    Boolean place(Coordinate mOriginPoint, EntityBlock b);

    /**
     * A getter for the grid size.
     * @return a coordinate where the x is the width and the y is the height of the grid.
     */
    Coordinate getSize();

    /**
     * Get a snapshot of the grid.
     * @return a snapshot of the grid.
     */
    Map<Coordinate, LevelGenerationEntity> getSnapshot();

    /**
     * Get the entity that the grid uses as placeholder for empty blocks.
     * @return the entity that the grid uses as placeholder for empty blocks.
     */
    LevelGenerationEntity getVoid();
}