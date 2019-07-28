package model.levelsgenerator;

import java.util.List;

/**
 * Block is a class that represent the occupational space of an entity in the grid level construction, is a list of point
 * that will be converted in relative coordinates in the matrix based on the "spawn point".
 */
public interface Block {

    /**
     * add a point to the block.
     * @param c is the point to add to the block.
     */
    void addPoint(Coordinate c);

    /**
     * get the relative coordinates of the points that form the block based on the spawn point.
     * @return the list of relative coordinates.
     */
    List<Coordinate> getRelativeCoordinates();

    /**
     * get the number of points that compose the block.
     * @return the number of points that compose the block.
     */
    Integer getOccupation();

}
