package model.levelsgenerator;

import java.util.Optional;

/**
 * 
 * a class for the matrix generation and blocks insertion.
 *
 */
public interface Grid {

    /**
     * reinitialize the matrix to the starting status.
     */
    void reset();

    /**
     * get the matrix element at elemCoordinates.
     * @param elemCoordinates are the integer coordinates of the desired element.
     * @return an Optional<Integer> of the matrix element at elemCoordinates if exist.
     */
    Optional<Integer> getElement(Point elemCoordinates);

    /**
     * try to place the block b in the matrix with spawnPoint as its center.
     * @param spawnPoint is the "center" of the block where the absolute coordinates of the block are translated in relative coordinates in the matrix.
     * @return true if the block is placed, false if the placing is failed.
     */
    Boolean tryToPlace(Point spawnPoint, Block b);
}
