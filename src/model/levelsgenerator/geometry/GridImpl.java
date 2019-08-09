package model.levelsgenerator.geometry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.levelsgenerator.EntityBlock;
import model.levelsgenerator.LevelGenerationEntity;

/**
 * Implements the Grid interface using the LevelGenerationEntity abstraction for entities placing.
 * The static private entity VOID represents a empty block of the grid. 
 */
public class GridImpl implements Grid {

    private static final LevelGenerationEntity<?> VOID = new LevelGenerationEntity<>();
    private final Map<Coordinate, LevelGenerationEntity<?>> matrix;
    private final Coordinate size;

    /**
     * Initialize the matrix.
     * @param rows is the number of rows of the matrix.
     * @param columns is the number of columns of the matrix.
     */
    public GridImpl(final int rows, final int columns) {
        this.matrix = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix.put(new Coordinate(j, i), GridImpl.VOID);
            }
        }
        this.size = new Coordinate(columns, rows);
    }

    @Override
    public final List<Coordinate> getOverlap(final Coordinate mOriginPoint, final BlockImpl b) {
        return b.getRelativeCoordinates().stream()
                                         .map(p -> mOriginPoint.sum(p))
                                         .filter(p -> this.isInMatrixBounds(p))
                                         .collect(Collectors.toList());
    }

    @Override
    public final Boolean place(final Coordinate mOriginPoint, final EntityBlock b) {
        final List<Coordinate> overlap = this.getOverlap(mOriginPoint, b);

        /*if part of the block is out of bounds (so the overlap size is lesser than the size of the block)
         * or not every requested tile in the matrix is free (VOID), return false */
        if (overlap.size() < b.getOccupation() || !(overlap.stream().map(c -> this.getElement(c)).allMatch(e -> e.equals(GridImpl.VOID)))) {
            return false;
        } else {
            for (final Coordinate c : overlap) {
                this.setElement(c, b.getEntity());
            }
        }
            return true;
     }

    @Override
    public final void reset() {
        this.matrix.keySet().stream().forEach(k -> this.matrix.put(k, GridImpl.VOID));
    }

    @Override
    public final LevelGenerationEntity<?> getElement(final Coordinate elemCoordinates) throws IllegalArgumentException {
        if (this.isInMatrixBounds(elemCoordinates)) {
            return this.matrix.get(elemCoordinates);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * A getter for the grid size.
     * @return a coordinate where the x is the width and the y is the height of the grid.
     */
    public Coordinate getSize() {
        return this.size;
    }

    /**
     * Check if a coordinate is in matrix bounds.
     * @param elemCoordinates is the coordinates to check.
     * @return true if the coordinate is in matrix bounds, false otherwise.
     */
    public Boolean isInMatrixBounds(final Coordinate elemCoordinates) {
        return (elemCoordinates.getPoint().x >= 0 && elemCoordinates.getPoint().x < this.getSize().getPoint().x 
                && elemCoordinates.getPoint().y >= 0 && elemCoordinates.getPoint().y < this.getSize().getPoint().y);
    }

    /**
     * Get the entity that the grid uses as placeholder for empty blocks.
     * @return the entity that the grid uses as placeholder for empty blocks.
     */
    public LevelGenerationEntity<?> getVoid() {
        return GridImpl.VOID;
    }

    /**
     * A setter for a single element of the matrix.
     * @param elemCoordinates is the element's place in the matrix.
     * @param value is the LevelGeneration entity that the block will represent from now on.
     * @throws IllegalArgumentException if the coordinates are out of bounds.
     */
    public void setElement(final Coordinate elemCoordinates, final LevelGenerationEntity<?> value) throws IllegalArgumentException {
        if (this.isInMatrixBounds(elemCoordinates)) {
            this.matrix.put(elemCoordinates, value);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get a snapshot of the grid.
     * @return a snapshot of the grid.
     */
    public Map<Coordinate, LevelGenerationEntity<?>> getSnapshot() {
        return this.matrix;
    }
}
