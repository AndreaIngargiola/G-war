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
    }

    @Override
    public final List<Coordinate> getOverlap(final Coordinate mOriginPoint, final EntityBlock b) {
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
     * Check if a coordinate is in matrix bounds.
     * @param elemCoordinates is the coordinates to check.
     * @return true if the coordinate is in matrix bounds, false otherwise.
     */
    private Boolean isInMatrixBounds(final Coordinate elemCoordinates) {
        int maxX = this.matrix.keySet().stream()
                                       .map(c -> c.getPoint().x)
                                       .max((x1, x2) -> x1.compareTo(x2))
                                       .get();

        int maxY = this.matrix.keySet().stream()
                                       .map(c -> c.getPoint().y)
                                       .max((y1, y2) -> y1.compareTo(y2))
                                       .get();

        return (elemCoordinates.getPoint().x >= 0 && elemCoordinates.getPoint().x < maxX 
                && elemCoordinates.getPoint().y >= 0 && elemCoordinates.getPoint().y < maxY);
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
     */
    private void setElement(final Coordinate elemCoordinates, final LevelGenerationEntity<?> value) throws IllegalArgumentException {
        if (this.isInMatrixBounds(elemCoordinates)) {
            this.matrix.put(elemCoordinates, value);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
